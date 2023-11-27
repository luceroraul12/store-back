package distribuidora.scrapping.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductCustomerDto;
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
		if(CollectionUtils.isNotEmpty(order.getProducts())) {
			List<OrderHasProduct> orderHasProducts = orderHasProductConverter.toEntidadList(order.getProducts());
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
			orderHasProducts = orderHasProductRepository.saveAll(orderHasProducts);
			result =  orderHasProductConverter.toDtoList(orderHasProducts);
		}
		return result;
	}

	private Client validateClient(OrderDto order) throws Exception {
		// Verifico si el usuario ya existe
		Client client = clientDataService.getByCode(order.getStoreCode());
		// En caso de que no exista lo voy a registrar
		if(client == null)
			throw new Exception("No existe la tienda solicitada");
		return client;
	}

	private Customer validateCustomer(OrderDto order) {
		// Verifico si el usuario ya existe
		Customer customer = customerRepository.findByUsername(order.getUsername());
		// En caso de que no exista lo voy a registrar
		if(customer == null) {
			customer = new Customer();
			customer.setUsername(order.getUsername());
			customer = customerRepository.save(customer);
		}
		return customer;
	}

}
