package distribuidora.scrapping.controller;

import com.itextpdf.text.DocumentException;

import distribuidora.scrapping.dto.CategoryHasUnitDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.services.internal.ProductoInternoStatusService;
import distribuidora.scrapping.services.pdf.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
}
