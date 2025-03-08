package distribuidora.scrapping.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.repositories.CategoryRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.converters.CategoryDtoConverter;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	UsuarioService userService;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryDtoConverter categoryDtoConverter;

	@Autowired
	LookupService lookupService;

	@Autowired
	ProductoInternoRepository productoInternoRepository;

	@Override
	public List<CategoryDto> getCategories() {
		return categoryDtoConverter
				.toDtoList(categoryRepository.findCategoriesByClientId(userService.getCurrentClient().getId()));
	}

	@Override
	public CategoryDto createUpdateCategory(CategoryDto dto) throws Exception {
		if (dto == null)
			throw new Exception("Debe pasar la categoria");
		if (dto.getName() == null)
			throw new Exception("Debe pasar el nombre de la categoría");
		if (dto.getLvUnit() == null)
			throw new Exception("Debe pasar la unidad de la categoría");
		LookupValor unit = lookupService.getLookupValueByCode(dto.getLvUnit().getCode());
		if (unit == null)
			throw new Exception("La unidad enviada no es válida");
		Client currentClient = userService.getCurrentClient();
		Category categoryExisted = categoryRepository.findCategoryByNameAndClientId(dto.getName(),
				currentClient.getId());
		if (categoryExisted != null && !categoryExisted.getId().equals(dto.getId())
				&& categoryExisted.getName().equalsIgnoreCase(dto.getName()))
			throw new Exception("Ya existe una categoría con el nombre enviado");
		Category category = categoryDtoConverter.toEntidad(dto);
		category.setUnit(unit);
		category.setClient(currentClient);
		category = categoryRepository.save(category);
		return categoryDtoConverter.toDto(category);
	}

	@Override
	public Integer deleteCategoryById(Integer id) throws Exception {
		if (productoInternoRepository.hasProductWithCategoryId(id))
			throw new Exception("La categoría que intenta eliminar cuenta con productos asociados");
		categoryRepository.deleteById(id);
		return id;
	}

	@Override
	public Category getById(Integer id) throws Exception {
		Optional<Category> result = categoryRepository.findById(id);
		if(result.isEmpty())
			throw new Exception("No existe la categoría");
		return result.get();
	}

}
