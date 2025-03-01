package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.services.UsuarioService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	UsuarioService userService;
	
	@GetMapping("/modules")
	List<LookupValueDto> getModules(){
		return userService.getModules();
	}

}
