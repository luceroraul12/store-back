package distribuidora.scrapping.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constants;
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

	@Autowired
	PresentationService unitService;

	@Override
	public List<CategoryDto> getCategoryDtos() {
		return categoryDtoConverter.toDtoList(getCategories());
	}

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findCategoriesByClientIdAndType(userService.getCurrentClient().getId(),
				Constants.LV_CATEGORY_TYPE_PRODUCT);
	}

	@Override
	public List<Category> getCategoriesByType(String typeCode) {
		return categoryRepository.findCategoriesByClientIdAndType(userService.getCurrentClient().getId(), typeCode);
	}

	@Override
	public List<CategoryDto> getCategoryDtosByType(String typeCode) {
		return categoryDtoConverter.toDtoList(getCategoriesByType(typeCode));
	}

	@Override
	public CategoryDto createUpdateCategory(CategoryDto dto) throws Exception {
		if (dto == null)
			throw new Exception("Debe pasar la categoria");
		if (dto.getName() == null)
			throw new Exception("Debe pasar el nombre de la categoría");
		if (dto.getCategoryType() == null || StringUtils.isBlank(dto.getCategoryType().getCode()))
			throw new Exception("Debe pasar el tipo de categoría");
		Client currentClient = userService.getCurrentClient();
		Category categoryExisted = null;
		categoryExisted = null;
		if (dto.getId() != null)
			categoryExisted = categoryRepository.findById(dto.getId()).orElse(null);
		if (categoryExisted == null)
			categoryExisted = categoryRepository.findCategoryByNameAndClientIdAndType(dto.getName(),
					currentClient.getId(), dto.getCategoryType().getCode());

		if (categoryExisted != null && !categoryExisted.getId().equals(dto.getId())
				&& categoryExisted.getName().equalsIgnoreCase(dto.getName()))
			throw new Exception("Ya existe una categoría con el nombre enviado");
		LookupValor categoryType = lookupService.getLookupValueByCode(dto.getCategoryType().getCode());
		if (categoryType == null || categoryType.getLookupTipo() == null
				|| !Constants.LT_CATEGORY_TYPE.equals(categoryType.getLookupTipo().getCodigo()))
			throw new Exception("El tipo de categoría no es válido");

		if (categoryExisted != null && !categoryExisted.getCategoryType().getCodigo().equals(categoryType.getCodigo()))
			throw new Exception("No está permitido cambiar el tipo de una categoría existente");
		Category category = categoryDtoConverter.toEntidad(dto);
		category.setClient(currentClient);
		category.setCategoryType(categoryType);
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
		if (result.isEmpty())
			throw new Exception("No existe la categoría");
		return result.get();
	}

}
