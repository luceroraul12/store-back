package distribuidora.scrapping.services;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import distribuidora.scrapping.configs.Constants;
import distribuidora.scrapping.dto.CashRegisterSessionDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.customer.CashRegisterSession;
import distribuidora.scrapping.repositories.CashRegisterSessionRepository;
import distribuidora.scrapping.repositories.CartPaymentRepository;
import distribuidora.scrapping.util.DateUtil;
import distribuidora.scrapping.util.converters.CashRegisterSessionConverter;

@Service
public class CashRegisterSessionServiceImpl implements CashRegisterSessionService {

	@Autowired
	UsuarioService userService;

	@Autowired
	CashRegisterSessionRepository cashRegisterSessionRepository;

	@Autowired
	CashRegisterSessionConverter cashRegisterSessionConverter;

	@Autowired
	CartPaymentRepository cartPaymentRepository;

	@Override
	@Transactional
	public CashRegisterSessionDto openSession(CashRegisterSessionDto dto) throws Exception {
		Client client = userService.getCurrentClient();
		if (client == null)
			throw new Exception("No existe la tienda solicitada");

		cashRegisterSessionRepository.findOpenSessionByClientId(client.getId())
				.ifPresent(s -> {
					throw new RuntimeException("Ya existe una sesion de caja abierta para esta tienda");
				});

		CashRegisterSession session = new CashRegisterSession();
		session.setClient(client);
		session.setOpeningDate(new Date());
		session.setInitialAmount(dto.getInitialAmount());
		session.setStatus(Constants.CASH_REGISTER_STATUS_OPEN);
		session.setOpenedBy(userService.getCurrentUser().getUsuario());

		session = cashRegisterSessionRepository.save(session);
		return cashRegisterSessionConverter.toDto(session);
	}

	@Override
	@Transactional
	public CashRegisterSessionDto closeSession(CashRegisterSessionDto dto) throws Exception {
		Client client = userService.getCurrentClient();
		if (client == null)
			throw new Exception("No existe la tienda solicitada");

		CashRegisterSession session = cashRegisterSessionRepository.findOpenSessionByClientId(client.getId())
				.orElseThrow(() -> new Exception("No hay sesion de caja abierta"));

		session.setClosingDate(new Date());
		session.setCountedAmount(dto.getCountedAmount());
		session.setStatus(Constants.CASH_REGISTER_STATUS_CLOSED);
		session.setClosedBy(userService.getCurrentUser().getUsuario());

		session = cashRegisterSessionRepository.save(session);
		return cashRegisterSessionConverter.toDto(session);
	}

	@Override
	public CashRegisterSessionDto getCurrentSession() throws Exception {
		Client client = userService.getCurrentClient();
		if (client == null)
			throw new Exception("No existe la tienda solicitada");

		CashRegisterSession session = cashRegisterSessionRepository.findOpenSessionByClientId(client.getId())
				.orElse(null);
		if (session == null)
			return null;

		return cashRegisterSessionConverter.toDto(session);
	}

	@Override
	public Page<CashRegisterSessionDto> getHistory(LocalDate dateFrom, LocalDate dateTo, Integer pageIndex,
			Integer size) throws Exception {
		Client client = userService.getCurrentClient();
		if (client == null)
			throw new Exception("No existe la tienda solicitada");

		if (pageIndex == null)
			pageIndex = 0;
		if (size == null)
			size = 10;
		Date df = DateUtil.getStartDate(dateFrom);
		Date dt = DateUtil.getEndDate(dateTo);
		PageRequest pageable = PageRequest.of(pageIndex, size);
		Page<CashRegisterSession> page = cashRegisterSessionRepository.findByClientIdAndDateRange(client.getId(), df,
				dt, pageable);
		return cashRegisterSessionConverter.toPage(page);
	}
}
