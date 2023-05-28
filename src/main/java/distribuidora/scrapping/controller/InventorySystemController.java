package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/inventory-system/")
public class InventorySystemController {

	@Autowired
	ProductoInternoRepository repository;

	@Autowired
	InventorySystem service;

	@PostMapping(value = "createUpdate")
	List<ProductoInternoDto> crearModificarProductos(@RequestBody(required = false) List<ProductoInternoDto> dtos){
		return service.crearActualizarProductos(dtos);
	}

	@GetMapping
	List<ProductoInternoDto> getProductos(){
		return service.getProductos();
	}

	@DeleteMapping
	List<ProductoInternoDto> eliminarProductos(@RequestBody List<Integer> dtos){
		return service.eliminarProductos(dtos);
	}
}
