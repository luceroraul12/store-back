package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductCustomerDto;

@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public List<ProductCustomerDto> createOrder(OrderDto order) {
		return null;
	}

}
