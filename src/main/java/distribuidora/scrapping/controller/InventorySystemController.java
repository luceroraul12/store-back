package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/inventory-system/")
public class InventorySystemController {

	@Autowired
	ProductoInternoRepository repository;

	@PostMapping("create")
	void crearProductos(List<ProductoInternoDto> dtos){

	}

	@GetMapping()
	List<ProductoInterno> getProductos(){
		return repository.findAll();
	}
}
