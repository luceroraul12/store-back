package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductOrderDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.customer.Customer;
import distribuidora.scrapping.entities.customer.Order;
import distribuidora.scrapping.entities.customer.OrderHasProduct;
import distribuidora.scrapping.repositories.CustomerRepository;
import distribuidora.scrapping.repositories.OrderHasProductRepository;
import distribuidora.scrapping.repositories.OrderRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
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

	@Override
	public List<ProductOrderDto> createOrder(OrderDto order) throws Exception {
		Customer customer = validateCustomer(order);
		// Verifico que codigo de cliente exista
		Client client = validateClient(order);
		List<ProductOrderDto> result = null;
		// Busco los productos solicitados
		if (CollectionUtils.isNotEmpty(order.getProducts())) {
			List<OrderHasProduct> orderHasProducts = orderHasProductConverter
					.toEntidadList(order.getProducts());
			// Creo la orden
			Order o = new Order();
			o.setClient(client);
			o.setCustomer(customer);
			o.setDate(new Date());
			o = orderRepository.save(o);
			// Seteo la orden a todos los productos
			for (OrderHasProduct orderHasProduct : orderHasProducts) {
				orderHasProduct.setOrder(o);
			}
			// Guardo todos los order product
			orderHasProducts = orderHasProductRepository
					.saveAll(orderHasProducts);
			result = orderHasProductConverter.toDtoList(orderHasProducts);
		}
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
	
	

}
