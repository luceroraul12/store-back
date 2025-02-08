package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.entities.customer.Cart;

@Component
public class CartDtoConverter extends Converter<Cart, CartDto> {

	@Autowired
	PersonDtoConverter personDtoConverter;

	@Override
	public CartDto toDto(Cart entidad) {
		CartDto dto = new CartDto();
		dto.setCartId(entidad.getId());
		dto.setBackendCartId(entidad.getId());
		dto.setDateCreated(entidad.getDateCreated());
		dto.setStatus(entidad.getStatus());
		dto.setTotalPrice(entidad.getTotalPrice());
		dto.setCustomer(personDtoConverter.toDto(entidad.getCustomer()));
		return dto;
	}

	@Override
	public Cart toEntidad(CartDto dto) {
		return null;
	}

}
