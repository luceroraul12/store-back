package distribuidora.scrapping.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import distribuidora.scrapping.dto.ExpenseDto;

public interface ExpenseService {
	ExpenseDto createExpense(ExpenseDto dto) throws Exception;

	Page<ExpenseDto> getExpenses(LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size) throws Exception;

	Integer deleteExpenseById(Integer id) throws Exception;
}
