package distribuidora.scrapping.services;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.UnitDto;
import distribuidora.scrapping.entities.Unit;
import distribuidora.scrapping.util.converters.Converter;

@Component
public class UnitDtoConverter extends Converter<Unit, UnitDto>{

	@Override
	public UnitDto toDto(Unit e) {
		UnitDto d = new UnitDto();
		d.setId(e.getId());
		d.setName(e.getName());
		d.setRelation(e.getRelation());
		d.setDescription(e.getDescription());
		d.setPdfShowChild(e.getPdfShowChild());
		d.setSelectable(e.getSelectable());
		if(e.getUnitParent() != null)
			d.setUnitParent(toDto(e.getUnitParent()));
		return d;
	}

	@Override
	public Unit toEntidad(UnitDto d) {
		// TODO Auto-generated method stub
		return null;
	}

}
