package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductCustomerDto;

public interface OrderService {

	List<ProductCustomerDto> createOrder(OrderDto order);

}
