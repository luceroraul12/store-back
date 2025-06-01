package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.UnitDto;
import distribuidora.scrapping.entities.Unit;

@Component
public class UnitDtoConverter extends Converter<Unit, UnitDto>{

	@Override
	public UnitDto toDto(Unit e) {
		UnitDto dto = new UnitDto();
		dto.setId(e.getId());
		dto.setName(e.getName());
		dto.setSymbol(e.getSymbol());
		dto.setRelation(e.getRelation());
		return dto;
	}

	@Override
	public Unit toEntidad(UnitDto d) {
		// TODO Auto-generated method stub
		return null;
	}

}
