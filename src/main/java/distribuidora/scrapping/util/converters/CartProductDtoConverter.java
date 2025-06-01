package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CartProductDto;
import distribuidora.scrapping.entities.customer.CartProduct;
import distribuidora.scrapping.services.PresentationDtoConverter;

@Component
public class CartProductDtoConverter extends Converter<CartProduct, CartProductDto> {
	
	@Autowired
	UnitDtoConverter unitDtoConverter;

	@Override
	public CartProductDto toDto(CartProduct entidad) {
		CartProductDto dto = new CartProductDto();
		dto.setBackendCartProductId(entidad.getCart().getId());
		dto.setPrice(entidad.getPrice());
		dto.setProductId(entidad.getProduct().getId());
		dto.setName(entidad.getProduct().getNombre());
		dto.setDescription(entidad.getProduct().getDescripcion());
		dto.setQuantity(entidad.getQuantity());
		dto.setUnit(unitDtoConverter.toDto(entidad.getUnit()));
		return dto;
	}

	@Override
	public CartProduct toEntidad(CartProductDto dto) {
		return null;
	}

}
