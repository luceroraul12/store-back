package distribuidora.scrapping.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.CashRegisterSessionDto;
import distribuidora.scrapping.services.CashRegisterSessionService;

@RestController
@RequestMapping("/cash-register")
public class CashRegisterController {

	@Autowired
	CashRegisterSessionService cashRegisterSessionService;

	@PostMapping("/open")
	CashRegisterSessionDto openSession(@RequestBody CashRegisterSessionDto dto) throws Exception {
		return cashRegisterSessionService.openSession(dto);
	}

	@PostMapping("/close")
	CashRegisterSessionDto closeSession(@RequestBody CashRegisterSessionDto dto) throws Exception {
		return cashRegisterSessionService.closeSession(dto);
	}

	@GetMapping("/current")
	CashRegisterSessionDto getCurrentSession() throws Exception {
		return cashRegisterSessionService.getCurrentSession();
	}
	
	@GetMapping("/last-closed")
	CashRegisterSessionDto getLastCloseSession() throws Exception {
		return cashRegisterSessionService.getLastCloseSession();
	}

	@GetMapping()
	Page<CashRegisterSessionDto> getHistory(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size)
			throws Exception {
		return cashRegisterSessionService.getHistory(dateFrom, dateTo, page, size);
	}
}
