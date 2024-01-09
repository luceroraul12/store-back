package distribuidora.scrapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.OrderDto;
import distribuidora.scrapping.services.OrderService;

@RestController
@RequestMapping("/order/")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("/order/authorize")
	OrderDto authorizeOrder(@RequestBody OrderDto dto) {
		return orderService.authorizeOrder(dto);
	}

	@PostMapping("/order/finalize")
	OrderDto finalizeOrder(@RequestBody OrderDto dto) {
		return orderService.finalizeOrder(dto);
	}

}
