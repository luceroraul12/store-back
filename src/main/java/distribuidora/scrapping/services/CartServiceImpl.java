package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.Client;
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

	@Override
	public List<CartDto> createFinalizedCart(List<CartDto> data) throws Exception {
		// TODO: Separar entre converters y service
		// Obtengo el cliente
		Client client = validateClient();
		List<Integer> productIds = data.stream().map(d -> d.getProducts()).flatMap(List::stream)
				.map(d -> d.getProductId()).distinct().toList();
		List<ProductoInterno> products = inventoryService.getProductByIds(productIds);

		// Creo las ordenes
		for (CartDto cartDto : data) {
			Person person = personService.getById(cartDto.getCustomer().getId());
			Cart cart = new Cart(client, person, cartDto.getDateCreated(), "SYNCHRONIZED", cartDto.getTotalPrice(),
					cartDto.getTotalPriceCustomer(), cartDto.getDiscount());
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
	public List<CartDto> getCarts(Integer personId) {
		UsuarioEntity user = userService.getCurrentUser();
		Client client = clientHasUsersRepository.findByClientId(user.getId()).getClient();

		List<CartProduct> data = orderHasProductRepository.findByClientIdAndPersonId(client.getId(), personId);
		List<CartDto> result = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(data)) {
			// Agrupo por cart
			Map<Cart, List<CartProduct>> mapDataByCart = data.stream().collect(Collectors.groupingBy(d -> d.getCart()));
			// Itero cara key
			mapDataByCart.forEach((cart, products) -> {
				CartDto dto = cartDtoConverter.toDto(cart);
				dto.setProducts(cartProductDtoConverter.toDtoList(products));
				result.add(dto);
			});
			result.sort((a, b) -> b.getDateCreated().compareTo(a.getDateCreated()));
		}
		return result;
	}

	@Override
	public void deleteById(Integer cartId) {
		// Elimino productos
		List<CartProduct> products = orderHasProductRepository.findByCartId(cartId);
		orderHasProductRepository.deleteAll(products);
		// Elimino pedido
		orderRepository.deleteById(cartId);
	}

	@Override
	public boolean hasCartByCustomerId(Integer id) {
		return orderRepository.hasCartByCustomerId(id);
	}

}
