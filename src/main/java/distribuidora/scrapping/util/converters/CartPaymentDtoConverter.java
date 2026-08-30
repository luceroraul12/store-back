package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CartPaymentDto;
import distribuidora.scrapping.entities.customer.CartPayment;

@Component
public class CartPaymentDtoConverter extends Converter<CartPayment, CartPaymentDto> {

	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;

	@Override
	public CartPaymentDto toDto(CartPayment entidad) {
		CartPaymentDto dto = new CartPaymentDto();
		dto.setId(entidad.getId());
		dto.setAmount(entidad.getAmount());
		if (entidad.getPaymentMethod() != null)
			dto.setPaymentMethod(lookupValueDtoConverter.toDto(entidad.getPaymentMethod()));
		return dto;
	}

	@Override
	public CartPayment toEntidad(CartPaymentDto dto) {
		return null;
	}

}
