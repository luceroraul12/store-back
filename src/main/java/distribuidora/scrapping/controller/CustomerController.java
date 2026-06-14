package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CustomerDto;
import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.services.PersonService;
import distribuidora.scrapping.services.internal.InventorySystem;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	// TODO: Ver si hay que eliominar este controller de customers o si tiene que
	// quedarse
	@Autowired
	InventorySystem inventorySystem;

	@Autowired
	PersonService personService;

	@GetMapping("/check")
	public CustomerDto checkCustomerDto(@RequestParam String phone, @RequestParam Integer clientCode) throws Exception {
		return personService.checkCustomer(phone, clientCode);
	}

	@GetMapping("/products")
	public List<ProductCustomerDto> getProducts() throws Exception {
		return inventorySystem.getProductsForCustomer(null);
	}
	
	
	@GetMapping("/external/products")
	public List<ProductCustomerDto> getExternalProducts(@RequestParam Integer clientId) throws Exception {
		return inventorySystem.getProductsForCustomer(clientId);
	}
}
