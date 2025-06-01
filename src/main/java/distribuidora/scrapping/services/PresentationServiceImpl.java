package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.UnitDto;
import distribuidora.scrapping.entities.Unit;
import distribuidora.scrapping.repositories.UnitRepository;

@Service
public class UnitServiceImpl implements UnitService{
	
	@Autowired
	UnitRepository repository;
	
	@Autowired
	UsuarioService userService;
	
	@Autowired
	UnitDtoConverter converter;

	@Override
	public Unit getById(Integer id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("No existe la unidad."));
	}

	@Override
	public List<UnitDto> getUnits() {
		Integer clientId = userService.getCurrentClient().getId();
		return converter.toDtoList(repository.findByClientId(clientId, true));
	}
	
}
