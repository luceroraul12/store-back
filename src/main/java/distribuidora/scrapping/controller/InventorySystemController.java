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
}
