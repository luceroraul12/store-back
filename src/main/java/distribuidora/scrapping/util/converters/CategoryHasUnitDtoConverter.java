package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CategoryHasUnitDto;
import distribuidora.scrapping.entities.CategoryHasUnit;

@Component
public class CategoryHasUnitDtoConverter extends Converter<CategoryHasUnit, CategoryHasUnitDto>{
	
	@Autowired
	LookupValueDtoConverter converter;

	@Override
	public CategoryHasUnitDto toDto(CategoryHasUnit entidad) {
		CategoryHasUnitDto dto = new CategoryHasUnitDto();
		dto.setCategory(converter.toDto(entidad.getCategory()));
		dto.setUnit(converter.toDto(entidad.getUnit()));
		dto.setId(entidad.getId());
		return dto;
	}

	@Override
	public CategoryHasUnit toEntidad(CategoryHasUnitDto dto) {
		CategoryHasUnit entidad = new CategoryHasUnit();
		entidad.setCategory(converter.toEntidad(dto.getCategory()));
		entidad.setUnit(converter.toEntidad(dto.getUnit()));
		entidad.setId(dto.getId());
		return entidad;
	}


}
