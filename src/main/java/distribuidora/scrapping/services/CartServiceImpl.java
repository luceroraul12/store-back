package distribuidora.scrapping.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.Discount;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.Supplier;
import distribuidora.scrapping.entities.SupplierBalance;
import distribuidora.scrapping.entities.customer.Cart;
import distribuidora.scrapping.entities.customer.CartProduct;
import distribuidora.scrapping.repositories.CartProductRepository;
import distribuidora.scrapping.repositories.ClientHasUsersRepository;
import distribuidora.scrapping.repositories.OrderRepository;
import distribuidora.scrapping.repositories.SupplierBalanceRepository;
import distribuidora.scrapping.repositories.SupplierRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.util.DateUtil;
import distribuidora.scrapping.util.converters.CartDtoConverter;
import distribuidora.scrapping.util.converters.CartProductDtoConverter;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	UsuarioService userService;

	@Autowired
	ClientHasUsersRepository clientHasUsersRepository;

	@Autowired
	CartProductRepository orderHasProductRepository;

	@Autowired
	CategoryHasUnitRepository categoryHasUnitRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	SupplierBalanceRepository supplierBalanceRepository;

	@Autowired
	CartDtoConverter cartDtoConverter;

	@Autowired
	CartProductDtoConverter cartProductDtoConverter;

	@Autowired
	@Lazy
	PersonService personService;

	@Autowired
	LookupService lookupService;

	@Autowired
	InventorySystem inventoryService;

	@Lazy
	@Autowired
	DiscountService discountService;

	@Override
	@Transactional
	public List<CartDto> createFinalizedCart(List<CartDto> data) throws Exception {
		// Obtengo el cliente
		Client client = validateClient();
		List<Integer> productIds = data.stream().map(d -> d.getProducts()).flatMap(List::stream)
				.map(d -> d.getProductId()).distinct().toList();
		List<ProductoInterno> products = inventoryService.getProductByIds(productIds);

		List<Integer> discountIds = data.stream().filter(d -> d.getDiscount() != null).map(d -> d.getDiscount().getId())
				.toList();
		List<Discount> discounts = discountService.getDiscountsByIds(discountIds);

		// Creo las ordenes
		for (CartDto cartDto : data) {
			Person person = cartDto.getCustomer() != null ? personService.getById(cartDto.getCustomer().getId()) : null;
			Supplier supplier = null;
			if (cartDto.getSupplier() != null) {
				supplier = supplierRepository.findByIdAndClientId(cartDto.getSupplier().getId(), client.getId());
				if (supplier == null)
					throw new Exception("La distribuidora no existe para la tienda actual");
			}
			if (person == null && supplier == null)
				throw new Exception("El pedido debe tener una persona o una distribuidora");
			if (person != null && supplier != null)
				throw new Exception("El pedido no puede tener una persona y una distribuidora");
			Discount discount = null;
			if (cartDto.getDiscount() != null)
				discount = discounts.stream().filter(d -> d.getId().equals(cartDto.getDiscount().getId())).findFirst()
						.orElse(null);
	
			Cart cart = new Cart(client, person, supplier, cartDto.getDateCreated(), "SYNCHRONIZED", cartDto.getTotalPrice(),
					cartDto.getCustomerTotalPrice(), discount);
			cart = orderRepository.save(cart);
			if (supplier != null)
				createStoreCredit(supplier, cart);
			// Seteo id de cart
			cartDto.setBackendCartId(cart.getId());
			cartDto.setStatus("SYNCHRONIZED");
			List<CartProduct> finalProducts = new ArrayList<CartProduct>();
			for (CartProductDto cp : cartDto.getProducts()) {
				Integer productId = cp.getProductId();
				ProductoInterno currentProductRelation = products.stream().filter(p -> p.getId().equals(productId))
						.findFirst().orElse(null);

				CartProduct cartProduct = new CartProduct(currentProductRelation.getPresentation().getUnit(), cart,
						currentProductRelation, cp.getPrice(), cp.getQuantity());
				cartProduct = orderHasProductRepository.save(cartProduct);
				finalProducts.add(cartProduct);
			}
			cartDto.setProducts(cartProductDtoConverter.toDtoList(finalProducts));
		}

		return data;
	}

	private void createStoreCredit(Supplier supplier, Cart cart) {
		LookupValor balanceType = lookupService.getLookupValueByCode("STORE_CREDIT");
		SupplierBalance balance = new SupplierBalance();
		balance.setSupplier(supplier);
		balance.setCart(cart);
		balance.setBalanceType(balanceType);
		balance.setAmount(cart.getCustomerTotalPrice());
		supplierBalanceRepository.save(balance);
	}

	private Client validateClient() throws Exception {
		// Verifico si el usuario ya existe
		UsuarioEntity user = userService.getCurrentUser();
		Client client = clientHasUsersRepository.findByClientId(user.getId()).getClient();

		// En caso de que no exista lo voy a registrar
		if (client == null)
			throw new Exception("No existe la tienda solicitada");
		return client;
	}

	@Override
	public Page<CartDto> getCartsPage(Integer personId, LocalDate dateFrom, LocalDate dateTo, Integer pageIndex,
			Integer size) {
		UsuarioEntity user = userService.getCurrentUser();
		Client client = clientHasUsersRepository.findByClientId(user.getId()).getClient();

		// busco el paginado de los carts
		if (pageIndex == null)
			pageIndex = 0;
		if (size == null)
			size = 10;
		Date df = DateUtil.getStartDate(dateFrom);
		Date dt = DateUtil.getEndDate(dateTo);
		PageRequest pageable = PageRequest.of(pageIndex, size);
		Page<Cart> page = orderRepository.findPageByClientIdAndPersonId(client.getId(), personId, df, dt, pageable);
		Page<CartDto> result = cartDtoConverter.toPage(page);
		if (CollectionUtils.isNotEmpty(page.getContent())) {
			List<Integer> cartIds = page.getContent().stream().map(Cart::getId).toList();
			// busco los productos de todos los carts
			List<CartProduct> products = orderHasProductRepository.findByCartIds(cartIds);
			// Los agrego a cada cart
			result.getContent().forEach(c -> {
				List<CartProduct> currentCartProducts = products.stream()
						.filter(cp -> cp.getCart().getId().equals(c.getCartId())).toList();
				if (CollectionUtils.isNotEmpty(currentCartProducts))
					c.setProducts(cartProductDtoConverter.toDtoList(currentCartProducts));
			});
		}
		return result;
	}

	@Override
	public void deleteById(Integer cartId) {
		// Elimino productos
		List<CartProduct> products = orderHasProductRepository.findByCartIds(Arrays.asList(cartId));
		orderHasProductRepository.deleteAll(products);
		supplierBalanceRepository.deleteByCartId(cartId);
		// Elimino pedido
		orderRepository.deleteById(cartId);
	}

	@Override
	public boolean hasCartByCustomerId(Integer id) {
		return orderRepository.hasCartByCustomerId(id);
	}

	@Override
	public boolean hasCartsByDiscountId(Integer id) {
		return orderRepository.hasCartsByDiscountId(id);
	}

}
