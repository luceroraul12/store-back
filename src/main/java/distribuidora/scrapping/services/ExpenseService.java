package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.ExpenseDto;

public interface ExpenseService {
	ExpenseDto createExpense(ExpenseDto dto) throws Exception;

	List<ExpenseDto> getExpenses() throws Exception;

	Integer deleteExpenseById(Integer id) throws Exception;
}
