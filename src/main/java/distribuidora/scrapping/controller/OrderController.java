package distribuidora.scrapping.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("create")
	OrderDto createOrder(@RequestBody OrderDto dto) throws Exception {
		return orderService.createOrder(dto);
	}
	
	@PostMapping("update")
	OrderDto updateOrder(@RequestBody OrderDto dto) throws Exception {
		return orderService.updateOrder(dto);
	}

	@PostMapping("authorize")
	OrderDto authorizeOrder(@RequestBody OrderDto dto) {
		return orderService.authorizeOrder(dto);
	}

	@PostMapping("finalize")
	OrderDto finalizeOrder(@RequestBody OrderDto dto) {
		return orderService.finalizeOrder(dto);
	}

	@PostMapping("delete/{orderId}")
	OrderDto deleteOrder(@PathVariable Integer orderId) throws Exception {
		return orderService.deleteOrder(orderId);
	}
}
