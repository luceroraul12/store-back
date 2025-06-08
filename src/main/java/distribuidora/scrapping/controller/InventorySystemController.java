package distribuidora.scrapping.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.UsuarioService;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.services.pdf.PdfService;

@RestController()
@RequestMapping("/inventory-system/")
public class InventorySystemController {

	@Autowired
	ProductoInternoRepository repository;

	@Autowired
	InventorySystem service;

	@Autowired
	PdfService pdfService;

	@Autowired
	UsuarioService userService;

	@PostMapping(value = "product")
	ProductoInternoDto crearProducto(@RequestBody ProductoInternoDto dto) throws Exception {
		return service.crearProducto(dto);
	}

	@PutMapping(value = "product")
	ProductoInternoDto modificarProducto(@RequestBody ProductoInternoDto dto) throws Exception {
		return service.modificarProducto(dto);
	}

	@PutMapping(value = "updates")
	List<ProductoInternoDto> updateManyProducto(@RequestBody List<ProductoInternoDto> dtos) throws Exception {
		return service.updateManyProducto(dtos);
	}

	@GetMapping
	List<ProductoInternoDto> getProductos(@RequestParam(required = false) String search) throws Exception {
		return service.getProductDtos(search);
	}

	@DeleteMapping(value = "delete")
	List<ProductoInternoDto> eliminarProductos(@RequestBody List<Integer> dtos) {
		return service.eliminarProductos(dtos);
	}

	@PutMapping("available")
	void changeAvailable(@RequestParam(required = true) Integer productId,
			@RequestParam(required = true) Boolean isAvailable) throws Exception {
		service.changeAvailable(productId, isAvailable);
	}

	@GetMapping("updateAll")
	List<ProductoInternoDto> actualizarAllProductos() throws Exception {
		service.actualizarPreciosAutomatico();
		return getProductos(null);
	}

	@GetMapping("pdf")
	void getPDF(HttpServletResponse response) throws Exception {
		pdfService.getPdfByClientId(response, userService.getCurrentClient().getId());
	}

	@GetMapping("categories")
	List<CategoryDto> getCategoryDtoList() {
		return service.getCategoryDtoList();
	}

	@PutMapping("categories")
	CategoryDto updateCategoryHasUnit(@RequestBody CategoryDto dto) {
		return service.updateCategoryHasUnit(dto);
	}
}
