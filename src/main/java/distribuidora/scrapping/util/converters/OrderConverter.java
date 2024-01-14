package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.entities.customer.Order;

@Component
public class OrderConverter extends Converter<Order, OrderDto>{

	@Override
	public OrderDto toDto(Order entidad) {
		OrderDto dto = new OrderDto();
		dto.setDate(entidad.getDate());
		dto.setId(entidad.getId());
		dto.setStoreCode(entidad.getClient().getName());
		dto.setUsername(entidad.getCustomer().getUsername());
		return dto;
	}

	@Override
	public Order toEntidad(OrderDto dto) {
		Order e = new Order();
		e.setDate(dto.getDate());
		e.setId(dto.getId());
		return e;
	}

}
