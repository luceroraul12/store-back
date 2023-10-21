package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.services.internal.InventorySystem;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	InventorySystem service;
	
	@GetMapping("/products")
	public List<ProductoInternoDto> getProducts(){
		return service.getProductos(); 
	}

}
