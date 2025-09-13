package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.DiscountDto;
import distribuidora.scrapping.services.DiscountService;

@RestController
@RequestMapping("/discount")
public class DiscountController {

	@Autowired
	DiscountService service;

	@PostMapping
	public DiscountDto createUpdateDiscount(@RequestBody DiscountDto dto) throws Exception {
		return service.createUpdateDiscount(dto);
	}

	@DeleteMapping("/{id}")
	public Integer deleteDiscountById(@PathVariable Integer id) throws Exception {
		return service.deleteDiscountById(id);
	}

	@GetMapping
	public List<DiscountDto> getDiscounts() {
		return service.getClientDiscounts();
	}
}
