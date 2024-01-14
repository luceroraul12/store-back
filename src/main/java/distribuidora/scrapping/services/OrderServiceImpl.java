package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductOrderDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.customer.Customer;
import distribuidora.scrapping.entities.customer.Order;
import distribuidora.scrapping.entities.customer.OrderHasProduct;
import distribuidora.scrapping.repositories.CustomerRepository;
import distribuidora.scrapping.repositories.OrderHasProductRepository;
import distribuidora.scrapping.repositories.OrderRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.util.converters.OrderConverter;
import distribuidora.scrapping.util.converters.OrderHasProductConverter;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ClientDataService clientDataService;

	@Autowired
	private InventorySystem inventorySystem;

	@Autowired
	private OrderHasProductConverter orderHasProductConverter;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderHasProductRepository orderHasProductRepository;

	@Autowired
	private OrderConverter orderConverter;

	@Override
	public OrderDto createOrder(OrderDto order) throws Exception {
		Customer customer = validateCustomer(order);
		// Verifico que codigo de cliente exista
		Client client = validateClient(order);
		List<ProductOrderDto> resultOrderProducts = null;
		Order o = null;
		OrderDto result = null;
		// Busco los productos solicitados
		if (CollectionUtils.isNotEmpty(order.getProducts())) {
			List<OrderHasProduct> orderHasProducts = configOrderHasProduct(o,
					order.getProducts());
			// Creo la orden
			o = new Order();
			o.setClient(client);
			o.setCustomer(customer);
			o.setDate(new Date());
			o.setStatus(Constantes.ORDER_STATUS_PENDING);
			o = orderRepository.save(o);
			// Seteo la orden a todos los productos
			for (OrderHasProduct ohp : orderHasProducts) {
				ohp.setOrder(o);
			}
			// Guardo todos los order product
			orderHasProducts = orderHasProductRepository
					.saveAll(orderHasProducts);
			resultOrderProducts = orderHasProductConverter
					.toDtoList(orderHasProducts);
		} else {
			throw new Exception(
					"No esta permitido crear ordenes sin productos asociados");
		}
		result = orderConverter.toDto(o);
		result.setProducts(resultOrderProducts);

		return result;
	}

	private Client validateClient(OrderDto order) throws Exception {
		// Verifico si el usuario ya existe
		Client client = clientDataService.getByCode(order.getStoreCode());
		// En caso de que no exista lo voy a registrar
		if (client == null)
			throw new Exception("No existe la tienda solicitada");
		return client;
	}

	private Customer validateCustomer(OrderDto order) {
		// Verifico si el usuario ya existe
		Customer customer = customerRepository
				.findByUsername(order.getUsername());
		// En caso de que no exista lo voy a registrar
		if (customer == null) {
			customer = new Customer();
			customer.setUsername(order.getUsername());
			customer = customerRepository.save(customer);
		}
		return customer;
	}

	@Override
	public List<OrderDto> getMyOrders(String storeCode, String username) {
		List<OrderHasProduct> relations = orderHasProductRepository
				.findByStoreCodeAndUsername(storeCode, username);
		// Agrupo por orden y las ordeno por fecha
		TreeMap<Order, List<OrderHasProduct>> treeProductByOrder = new TreeMap<>(
				(a, b) -> b.getDate().compareTo(a.getDate()));
		treeProductByOrder.putAll(relations.stream()
				.collect(Collectors.groupingBy(r -> r.getOrder())));
		List<OrderDto> result = new ArrayList();
		if (!treeProductByOrder.isEmpty()) {
			treeProductByOrder.forEach((o, p) -> {
				OrderDto dto = new OrderDto();
				dto.setId(o.getId());
				dto.setUsername(o.getCustomer().getUsername());
				dto.setStoreCode(o.getClient().getName());
				dto.setDate(o.getDate());
				dto.setProducts(orderHasProductConverter.toDtoList(p));
				result.add(dto);
			});

		}
		return result;
	}

	@Override
	public OrderDto authorizeOrder(OrderDto order) {
		// Me fijo los productos y las cantidades respecto a anteriormente
		// Persisto la orden en estado autorizado
		return null;
	}

	@Override
	public OrderDto finalizeOrder(OrderDto order) {
		// Debo reducir la cantidad de stock de los productos de la orden
		// Persisto la orden en estado finalizado
		return null;
	}

	@Override
	public OrderDto deleteOrder(Integer orderId) throws Exception {
		// Busco el pedido
		Order order = validateOrderExistAndActive(orderId);
		// Cambio el estado y persisto
		order.setStatus(Constantes.ORDER_STATUS_INACTIVE);
		order = orderRepository.save(order);
		// Busco los productos para poder mostrarlos
		OrderDto orderDto = orderConverter.toDto(order);
		List<OrderHasProduct> ohp = orderHasProductRepository
				.findAllByOrderId(orderId);
		orderDto.setProducts(orderHasProductConverter.toDtoList(ohp));
		return orderDto;
	}

	@Override
	public OrderDto updateOrder(OrderDto dto) throws Exception {
		// Me fijo que exista el pedido
		Order order = validateOrderExistAndActive(dto.getId());
		// Busco los productos asociados anteriores y los elimino
		List<OrderHasProduct> ohps = orderHasProductRepository
				.findAllByOrderId(order.getId());
		orderHasProductRepository.deleteAll(ohps);
		// Asocio nuevamente los productos que me manda
		List<OrderHasProduct> newOhps = configOrderHasProduct(order,
				dto.getProducts());
		// Persisto los nuevos productos
		newOhps = orderHasProductRepository.saveAll(newOhps);
		// Convierto y retorno el dto
		OrderDto result = orderConverter.toDto(order);
		result.setProducts(orderHasProductConverter.toDtoList(newOhps));
		return result;
	}

	private List<OrderHasProduct> configOrderHasProduct(Order order,
			List<ProductOrderDto> productsDto) throws Exception {
		// Me fijo los productos de la base
		List<Integer> productIds = productsDto.stream()
				.map(p -> p.getProductId()).toList();
		List<ProductoInterno> products = inventorySystem
				.getProductByIds(productIds);
		// Valido que la cantidad de productos enviados sea la misma a l aque
		// existen en la base de datos
		if (products.size() != productsDto.size())
			throw new Exception(
					"Alguno de los productos no existen en el sistema");
		List<OrderHasProduct> orderHasProducts = new ArrayList<>();
		for (ProductOrderDto ohpDto : productsDto) {
			OrderHasProduct ohp = orderHasProductConverter.toEntidad(ohpDto);
			ProductoInterno product = products.stream().findFirst()
					.orElse(null);
			ohp.setOrder(order);
			// Le agrego producto para que no tenga conflictos al momento de
			// hacer desconversion
			ohp.setProduct(product);
			orderHasProducts.add(ohp);
		}
		return orderHasProducts;
	}

	private Order validateOrderExistAndActive(Integer orderId)
			throws Exception {
		Order order = orderRepository.findById(orderId).orElse(null);
		// Me fijo que exista
		if (order == null)
			throw new Exception("La orden no existe");
		// Me fijo que no se encuentre eliminada previamente
		if (order.getStatus().equals(Constantes.ORDER_STATUS_INACTIVE))
			throw new Exception("El pedido ya se encuentra eliminado");
		return order;
	}
}
