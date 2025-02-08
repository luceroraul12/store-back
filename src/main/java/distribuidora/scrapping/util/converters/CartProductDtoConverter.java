package distribuidora.scrapping.util.converters;

import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.customer.CartProduct;

public class CartProductDtoConverter extends Converter<CartProduct, CartProductDto> {

	@Override
	public CartProductDto toDto(CartProduct entidad) {
		CartProductDto dto = new CartProductDto();
		dto.setBackendCartProductId(entidad.getCart().getId());
		dto.setPrice(entidad.getPrice());
		dto.setProductId(entidad.getProduct().getId());
		dto.setQuantity(entidad.getQuantity());
		return dto;
	}

	@Override
	public CartProduct toEntidad(CartProductDto dto) {
		return null;
	}

}
