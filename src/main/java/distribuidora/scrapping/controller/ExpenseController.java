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

import distribuidora.scrapping.dto.ExpenseDto;
import distribuidora.scrapping.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@GetMapping
	List<ExpenseDto> getExpenses() throws Exception {
		return expenseService.getExpenses();
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
