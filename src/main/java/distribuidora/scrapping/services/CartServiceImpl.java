package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.entities.customer.Cart;
import distribuidora.scrapping.entities.customer.CartProduct;
import distribuidora.scrapping.repositories.ClientHasUsersRepository;
import distribuidora.scrapping.repositories.CartProductRepository;
import distribuidora.scrapping.repositories.OrderRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.services.internal.ProductoInternoStatusService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	UsuarioService userService;

	@Autowired
	ClientHasUsersRepository clientHasUsersRepository;

	@Autowired
	CartProductRepository orderHasProductRepository;

	@Autowired
	ProductoInternoStatusService productoInternoStatusService;

	@Autowired
	CategoryHasUnitRepository categoryHasUnitRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public List<CartDto> createFinalizedCart(List<CartDto> data)
			throws Exception {
		// TODO: Separar entre converters y service
		// Obtengo el cliente
		Client client = validateClient();
		List<CategoryHasUnit> categories = categoryHasUnitRepository.findAll();
		List<Integer> productIds = data.stream().map(d -> d.getProducts())
				.flatMap(List::stream).map(d -> d.getProductId()).distinct()
				.toList();
		List<ProductoInternoStatus> products = productoInternoStatusService
				.getAllByProductIds(productIds);
		
		// Creo las ordenes
		for (CartDto cartDto : data) {
			Cart cart = new Cart(client, cartDto.getDateCreated(),
					"SYNCHRONIZED");
			cart = orderRepository.save(cart);
			// Seteo id de cart
			cartDto.setBackendCartId(cart.getId());
			cartDto.setStatus("SYNCHRONIZED");
			for (CartProductDto cp : cartDto.getProducts()) {
				ProductoInternoStatus currentProductRelation = products.stream()
						.filter(r -> r.getProductoInterno().getId()
								.equals(cp.getProductId()))
						.findFirst().orElse(null);
				CategoryHasUnit currentCategory = categories.stream()
						.filter(c -> c.getCategory().getId()
								.equals(currentProductRelation
										.getProductoInterno().getLvCategoria()
										.getId()))
						.findFirst().orElse(null);

				CartProduct cartProduct = new CartProduct(
						currentCategory.getUnit(), cart,
						currentProductRelation.getProductoInterno(),
						cp.getPrice(), cp.getQuantity());
				cartProduct = orderHasProductRepository.save(cartProduct);
				// Seteo ids
				cp.setBackendCartProductId(cartProduct.getId());
			}
		}
		
		return data;
	}

	private Client validateClient() throws Exception {
		// Verifico si el usuario ya existe
		UsuarioEntity user = userService.getCurrentUser();
		Client client = clientHasUsersRepository.findByClientId(user.getId())
				.getClient();

		// En caso de que no exista lo voy a registrar
		if (client == null)
			throw new Exception("No existe la tienda solicitada");
		return client;
	}

}
