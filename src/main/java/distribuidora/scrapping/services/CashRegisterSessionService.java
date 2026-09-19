package distribuidora.scrapping.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import distribuidora.scrapping.dto.CashRegisterSessionDto;

public interface CashRegisterSessionService {

	CashRegisterSessionDto openSession(CashRegisterSessionDto dto) throws Exception;

	CashRegisterSessionDto closeSession(CashRegisterSessionDto dto) throws Exception;

	CashRegisterSessionDto getCurrentSession() throws Exception;

	Page<CashRegisterSessionDto> getHistory(LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size)
			throws Exception;
}
