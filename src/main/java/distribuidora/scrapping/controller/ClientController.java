package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.dto.UnitDto;
import distribuidora.scrapping.services.UnitService;
import distribuidora.scrapping.services.UsuarioService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	UsuarioService userService;
	
	@Autowired
	UnitService unitService;
	
	@GetMapping("/modules")
	List<LookupValueDto> getModules(){
		return userService.getModules();
	}
	
	@GetMapping("/units")
	List<UnitDto> getUnits(){
		return unitService.getUnits();
	}


}
