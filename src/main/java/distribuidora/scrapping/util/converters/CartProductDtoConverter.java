package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.customer.CartProduct;

@Component
public class CartProductDtoConverter extends Converter<CartProduct, CartProductDto> {
	
	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;

	@Override
	public CartProductDto toDto(CartProduct entidad) {
		CartProductDto dto = new CartProductDto();
		dto.setBackendCartProductId(entidad.getCart().getId());
		dto.setPrice(entidad.getPrice());
		dto.setProductId(entidad.getProduct().getId());
		dto.setName(entidad.getProduct().getNombre());
		dto.setDescription(entidad.getProduct().getDescripcion());
		dto.setQuantity(entidad.getQuantity());
		dto.setLvUnit(lookupValueDtoConverter.toDto(entidad.getLvUnit()));
		return dto;
	}

	@Override
	public CartProduct toEntidad(CartProductDto dto) {
		return null;
	}

}
