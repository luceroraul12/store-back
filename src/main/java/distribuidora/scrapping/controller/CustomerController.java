package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.services.OrderService;
import distribuidora.scrapping.services.internal.ProductoInternoStatusService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	ProductoInternoStatusService service;
	
	@Autowired
	OrderService orderService;
	
	
	@GetMapping("/products")
	public List<ProductCustomerDto> getProducts(){
		return service.getProductsForCustomer(); 
	}
	
	@GetMapping("/order")
	public List<ProductCustomerDto> createOrder(@RequestBody OrderDto order){
		return orderService.createOrder(order); 
	}

}
