package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.entities.Category;

public interface CategoryService {

	List<CategoryDto> getCategoryDtos();

	List<Category> getCategories();

	List<Category> getCategoriesByType(String typeCode);

	List<CategoryDto> getCategoryDtosByType(String typeCode);

	CategoryDto createUpdateCategory(CategoryDto dto) throws Exception;

	Integer deleteCategoryById(Integer id) throws Exception;

	Category getById(Integer id) throws Exception;

}
