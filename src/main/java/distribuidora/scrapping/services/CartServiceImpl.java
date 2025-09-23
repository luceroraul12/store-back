package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.Discount;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.customer.Cart;
import distribuidora.scrapping.entities.customer.CartProduct;
import distribuidora.scrapping.repositories.CartProductRepository;
import distribuidora.scrapping.repositories.ClientHasUsersRepository;
import distribuidora.scrapping.repositories.OrderRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.services.internal.InventorySystem;
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
	public List<CartDto> createFinalizedCart(List<CartDto> data) throws Exception {
		// TODO: Separar entre converters y service
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
			Person person = personService.getById(cartDto.getCustomer().getId());
			Discount discount = null;
			if (cartDto.getDiscount() != null)
				discount = discounts.stream().filter(d -> d.getId().equals(cartDto.getDiscount().getId())).findFirst()
						.orElse(null);
			// TODO: Revisar si está bien que el front calcule el precio del descuento o si
			// el back tiene que volverlo a hacer por las dudas
			Cart cart = new Cart(client, person, cartDto.getDateCreated(), "SYNCHRONIZED", cartDto.getTotalPrice(),
					cartDto.getCustomerTotalPrice(), discount);
			cart = orderRepository.save(cart);
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
	public Page<CartDto> getCartsPage(Integer personId, Integer pageIndex, Integer size) {
		UsuarioEntity user = userService.getCurrentUser();
		Client client = clientHasUsersRepository.findByClientId(user.getId()).getClient();
		// busco el paginado de los carts
		if (pageIndex == null)
			pageIndex = 0;
		if (size == null)
			size = 10;
		PageRequest pageable = PageRequest.of(pageIndex, size);
		Page<Cart> page = orderRepository.findPageByClientIdAndPersonId(client.getId(), personId, pageable);
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
