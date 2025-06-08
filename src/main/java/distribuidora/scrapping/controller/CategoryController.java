package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@GetMapping
	List<CategoryDto> getCategories(){
		return categoryService.getCategoryDtos();
	}
	
	@PostMapping
	CategoryDto createUpdateCategory(@RequestBody CategoryDto dto) throws Exception{
		return categoryService.createUpdateCategory(dto);
	}
	
	@DeleteMapping("/{id}")
	Integer deleteCategoryById(@PathVariable Integer id) throws Exception{
		return categoryService.deleteCategoryById(id);
	}
}
