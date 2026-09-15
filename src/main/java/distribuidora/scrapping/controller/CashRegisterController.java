package distribuidora.scrapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
