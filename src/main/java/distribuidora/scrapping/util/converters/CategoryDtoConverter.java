package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.services.UnitDtoConverter;

@Component
public class CategoryDtoConverter extends Converter<Category, CategoryDto>{
	
	@Autowired
	UnitDtoConverter unitDtoConverter;

	@Override
	public CategoryDto toDto(Category entidad) {
		CategoryDto dto = new CategoryDto();
		dto.setId(entidad.getId());
		dto.setName(entidad.getName());
		dto.setDescription(entidad.getDescription());
		return dto;
	}

	@Override
	public Category toEntidad(CategoryDto dto) {
		Category e = new Category();
		e.setId(dto.getId());
		e.setName(dto.getName());
		e.setDescription(dto.getDescription());
		return e;
	}

}
