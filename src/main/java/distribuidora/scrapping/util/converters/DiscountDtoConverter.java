package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.DiscountDto;
import distribuidora.scrapping.entities.Discount;

@Component
public class DiscountDtoConverter extends Converter<Discount, DiscountDto> {

	@Override
	public DiscountDto toDto(Discount e) {
		DiscountDto dto = new DiscountDto();
		dto.setId(e.getId());
		dto.setName(e.getName());
		dto.setDescription(e.getDescription());
		dto.setPlainValue(e.getPlainValue());
		dto.setPercentageValue(e.getPercentageValue());
		return dto;
	}

	@Override
	public Discount toEntidad(DiscountDto d) {
		Discount e = new Discount();
		e.setId(d.getId());
		e.setName(d.getName());
		e.setDescription(d.getDescription());
		e.setPlainValue(d.getPlainValue());
		e.setPercentageValue(d.getPercentageValue());
		return e;
	}

}
