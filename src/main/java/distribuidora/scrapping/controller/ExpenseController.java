package distribuidora.scrapping.controller;

import java.time.LocalDate;

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

import distribuidora.scrapping.dto.ExpenseDto;
import distribuidora.scrapping.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@GetMapping
	Page<ExpenseDto> getExpenses(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size)
			throws Exception {
		return expenseService.getExpenses(dateFrom, dateTo, page, size);
	}

	@PostMapping
	ExpenseDto createExpense(@RequestBody ExpenseDto dto) throws Exception {
		return expenseService.createExpense(dto);
	}

	@DeleteMapping("/{id}")
	Integer deleteExpenseById(@PathVariable Integer id) throws Exception {
		return expenseService.deleteExpenseById(id);
	}
}
