package distribuidora.scrapping.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constants;
import distribuidora.scrapping.dto.ExpenseDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.Expense;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.customer.CashRegisterSession;
import distribuidora.scrapping.repositories.CategoryRepository;
import distribuidora.scrapping.repositories.postgres.ExpenseRepository;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.DateUtil;
import distribuidora.scrapping.util.converters.ExpenseConverter;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	UsuarioService userService;

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ExpenseConverter expenseConverter;

	@Autowired
	LookupService lookupService;

	@Autowired
	CashRegisterSessionService cashRegisterSessionService;

	@Override
	public ExpenseDto createExpense(ExpenseDto dto) throws Exception {
		if (dto == null || dto.getAmount() == null || dto.getAmount() <= 0)
			throw new Exception("Debe pasar un monto válido");
		if (dto.getCategory() == null || dto.getCategory().getId() == null)
			throw new Exception("Debe pasar una categoría");
		if (dto.getPaymentMethod() == null || StringUtils.isBlank(dto.getPaymentMethod().getCode()))
			throw new Exception("Debe pasar un método de pago");
		Category category = categoryRepository.findById(dto.getCategory().getId())
				.orElseThrow(() -> new Exception("No existe la categoría"));
		if (!Constants.LV_CATEGORY_TYPE_EXPENSE.equals(category.getCategoryType().getCodigo()))
			throw new Exception("La categoría no es de tipo gasto");
		Expense last = null;
		if (dto.getId() != null) {
			last = expenseRepository.findById(dto.getId()).orElse(null);
			validateExpense(last);
		}
		
		LookupValor paymentMethod = lookupService.getLookupValueByCode(dto.getPaymentMethod().getCode());
		if (paymentMethod == null)
			throw new Exception("Método de pago inválido");
		Expense expense = new Expense();
		expense.setCreationDate(new Date());
		expense.setClient(userService.getCurrentClient());
		expense.setAmount(dto.getAmount());
		expense.setCategory(category);
		expense.setPaymentMethod(paymentMethod);
		expense.setObservation(dto.getObservation());
		return expenseConverter.toDto(expenseRepository.save(expense));
	}

	@Override
	public Page<ExpenseDto> getExpenses(LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size)
			throws Exception {
		Date df = DateUtil.getStartDate(dateFrom);
		Date dt = DateUtil.getEndDate(dateTo);
		Page<Expense> expensePage = expenseRepository.findPageByParams(userService.getCurrentClient().getId(), df, dt,
				PageRequest.of(page, size));
		return expensePage.map(expenseConverter::toDto);
	}

	@Override
	public List<ExpenseDto> getExpenseDtosBySessionId(Integer sessionId) {
		CashRegisterSession session = cashRegisterSessionService.getById(sessionId);
		if (session == null) {
			return null;
		}

		Integer clientId = session.getClient().getId();
		Date dateFrom = session.getOpeningDate();
		Date dateTo = session.getClosingDate();
		List<Expense> carts = expenseRepository.findByClientIdAndDateRange(clientId, dateFrom != null, dateFrom,
				dateTo != null, dateTo);
		List<ExpenseDto> dtoList = expenseConverter.toDtoList(carts);
		return dtoList;
	}

	@Override
	public Integer deleteExpenseById(Integer id) throws Exception {
		Expense expense = expenseRepository.findByIdAndClientId(id, userService.getCurrentClient().getId());
		if (expense == null)
			throw new Exception("No existe el gasto");
		
		validateExpense(expense);
		
		expenseRepository.delete(expense);
		return id;
	}

	private void validateExpense(Expense expense) throws Exception {
		if (expense != null) {
			// Valido que no exista un cierre de caja en la fecha del pedido
			CashRegisterSession session = cashRegisterSessionService
					.getClosedSessionByClientIdAndDate(expense.getClient().getId(), expense.getCreationDate());
			if (session != null) {
				throw new Exception(
						String.format("No se puede eliminar ya que es parte del cierre con ID: %s", session.getId()));
			}
		}
		
	}
}
