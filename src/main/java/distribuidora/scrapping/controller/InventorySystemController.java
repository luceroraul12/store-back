package distribuidora.scrapping.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import distribuidora.scrapping.dto.CategoryHasUnitDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.services.internal.ProductoInternoStatusService;
import distribuidora.scrapping.services.pdf.PdfService;

@RestController()
@RequestMapping("/inventory-system/")
public class InventorySystemController {

	@Autowired
	ProductoInternoRepository repository;

	@Autowired
	InventorySystem service;

	@Autowired
	ProductoInternoStatusService productoInternoStatusService;

	@Autowired
	PdfService pdfService;

	@PostMapping(value = "create")
	ProductoInternoDto crearProducto(@RequestBody ProductoInternoDto dto){
		return service.crearProducto(dto);
	}

	@PutMapping(value = "update")
	ProductoInternoDto modificarProducto(@RequestBody ProductoInternoDto dto){
		return service.modificarProducto(dto);
	}

	@PutMapping(value = "updates")
	List<ProductoInternoDto> updateManyProducto(@RequestBody List<ProductoInternoDto> dtos){
		return service.updateManyProducto(dtos);
	}

	@GetMapping
	List<ProductoInternoDto> getProductos(){
		return service.getProductos();
	}

	@DeleteMapping(value = "delete")
	List<ProductoInternoDto> eliminarProductos(@RequestBody List<Integer> dtos){
		return service.eliminarProductos(dtos);
	}

	@GetMapping("updateAll")
	List<ProductoInternoDto> actualizarAllProductos(){
		service.actualizarPreciosAutomatico();
		return getProductos();
	}

	@GetMapping("pdf")
	void getPDF(HttpServletResponse response) throws DocumentException, IOException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=pasionaria-catalogo" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		pdfService.generatePdf(response);
	}

	@GetMapping("status")
	List<ProductoInternoStatusDto> getStatus(){
		return productoInternoStatusService.getAll();
	}

	@PutMapping("status")
	ProductoInternoStatusDto updateStatus(@RequestBody ProductoInternoStatusDto dto) {
		return productoInternoStatusService.update(dto);
	}
	
	@GetMapping("categories")
	List<CategoryHasUnitDto> getCategoryDtoList(){
		return service.getCategoryDtoList();
	}
	
	@PutMapping("categories")
	CategoryHasUnitDto updateCategoryHasUnit(@RequestBody CategoryHasUnitDto dto){
		return service.updateCategoryHasUnit(dto);
	}
}
