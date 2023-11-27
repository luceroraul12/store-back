package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.dto.ProductOrderDto;

public interface OrderService {

	List<ProductOrderDto> createOrder(OrderDto order) throws Exception;

}
