package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping
	List<CategoryDto> getCategories(
			@RequestParam(required = false) String typeCode) {
		return categoryService.getCategoryDtosByType(typeCode);
	}

	@PostMapping
	CategoryDto createUpdateCategory(@RequestBody CategoryDto dto) throws Exception {
		return categoryService.createUpdateCategory(dto);
	}

	@DeleteMapping("/{id}")
	Integer deleteCategoryById(@PathVariable Integer id) throws Exception {
		return categoryService.deleteCategoryById(id);
	}
}
