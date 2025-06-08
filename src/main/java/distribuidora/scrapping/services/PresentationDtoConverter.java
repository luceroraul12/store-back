package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.PresentationDto;
import distribuidora.scrapping.entities.Presentation;
import distribuidora.scrapping.util.converters.Converter;
import distribuidora.scrapping.util.converters.UnitDtoConverter;

@Component
public class PresentationDtoConverter extends Converter<Presentation, PresentationDto>{
	
	@Autowired
	UnitDtoConverter unitDtoConverter;

	@Override
	public PresentationDto toDto(Presentation e) {
		PresentationDto d = new PresentationDto();
		d.setId(e.getId());
		d.setName(e.getName());
		d.setRelation(e.getRelation());
		d.setDescription(e.getDescription());
		d.setUnit(unitDtoConverter.toDto(e.getUnit()));
		return d;
	}

	@Override
	public Presentation toEntidad(PresentationDto d) {
		// TODO Auto-generated method stub
		return null;
	}

}
