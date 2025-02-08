package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.entities.customer.Cart;

@Component
public class CartDtoConverter extends Converter<Cart, CartDto> {

	@Override
	public CartDto toDto(Cart entidad) {
		CartDto dto = new CartDto();
		dto.setCartId(entidad.getId());
		dto.setBackendCartId(entidad.getId());
		dto.setDateCreated(entidad.getDateCreated());
		dto.setStatus(entidad.getStatus());
		dto.setTotalPrice(entidad.getTotalPrice());
		return dto;
	}

	@Override
	public Cart toEntidad(CartDto dto) {
		return null;
	}

}
