package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.ProductoInterno;
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

	@PostMapping("create")
	Integer crearProductos(List<ProductoInternoDto> dtos){
		return service.crearProductos(dtos);
	}

	@GetMapping()
	List<ProductoInterno> getProductos(){
		return repository.findAll();
	}

	@DeleteMapping
	Boolean eliminarProductos(List<ProductoInternoDto> dtos){
		return null;
	}
}
