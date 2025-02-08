package distribuidora.scrapping.util.converters;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.entities.customer.Cart;

public class CartDtoConverter extends Converter<Cart, CartDto> {

	@Override
	public CartDto toDto(Cart entidad) {
		CartDto dto = new CartDto();
		dto.setCartId(entidad.getId());
		dto.setBackendCartId(entidad.getId());
		dto.setDateCreated(entidad.getDateCreated());
		dto.setStatus(entidad.getStatus());
		return dto;
	}

	@Override
	public Cart toEntidad(CartDto dto) {
		return null;
	}

}
