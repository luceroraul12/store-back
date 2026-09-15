package distribuidora.scrapping.services;

import distribuidora.scrapping.dto.CashRegisterSessionDto;

public interface CashRegisterSessionService {

	CashRegisterSessionDto openSession(CashRegisterSessionDto dto) throws Exception;

	CashRegisterSessionDto closeSession(CashRegisterSessionDto dto) throws Exception;

	CashRegisterSessionDto getCurrentSession() throws Exception;
}
