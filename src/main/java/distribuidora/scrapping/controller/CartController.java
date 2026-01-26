package distribuidora.scrapping.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CartDto;
import distribuidora.scrapping.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@PostMapping()
	List<CartDto> createFinalizedCarts(@RequestBody List<CartDto> data) throws Exception {
		return cartService.createFinalizedCart(data);
	}

	@GetMapping()
	Page<CartDto> getCarts(@RequestParam(required = false) Integer personId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size)
			throws Exception {
		return cartService.getCartsPage(personId, dateFrom, dateTo, page, size);
	}

	@DeleteMapping("/{id}")
	void deleteCartById(@PathVariable Integer id) throws Exception {
		cartService.deleteById(id);
	}
}
