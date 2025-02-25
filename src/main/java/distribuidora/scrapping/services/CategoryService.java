package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.CategoryDto;

public interface CategoryService {

	List<CategoryDto> getCategories();

	CategoryDto createUpdateCategory(CategoryDto dto) throws Exception;

	Integer deleteCategoryById(Integer id) throws Exception;

}
