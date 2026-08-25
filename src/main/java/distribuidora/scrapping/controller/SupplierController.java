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

import distribuidora.scrapping.dto.SupplierBalanceDto;
import distribuidora.scrapping.dto.SupplierDto;
import distribuidora.scrapping.services.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;

	@GetMapping
	public List<SupplierDto> getSuppliers() {
		return service.getSuppliers();
	}

	@PostMapping
	public SupplierDto createUpdateSupplier(@RequestBody SupplierDto dto) throws Exception {
		return service.createUpdateSupplier(dto);
	}

	@DeleteMapping("/{id}")
	public Integer deleteSupplier(@PathVariable Integer id) throws Exception {
		return service.deleteSupplier(id);
	}

	@GetMapping("/{supplierId}/balances")
	public List<SupplierBalanceDto> getBalances(@PathVariable Integer supplierId) throws Exception {
		return service.getBalances(supplierId);
	}

	@PostMapping("/{supplierId}/balances")
	public SupplierBalanceDto createBalance(@PathVariable Integer supplierId,
			@RequestBody SupplierBalanceDto dto) throws Exception {
		return service.createBalance(supplierId, dto);
	}

	@DeleteMapping("/{supplierId}/balances/{balanceId}")
	public Integer deleteBalance(@PathVariable Integer supplierId, @PathVariable Integer balanceId) throws Exception {
		return service.deleteBalance(supplierId, balanceId);
	}
}
