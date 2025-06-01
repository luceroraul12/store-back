package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.PresentationDto;
import distribuidora.scrapping.entities.Presentation;
import distribuidora.scrapping.repositories.PresentationRepository;

@Service
public class PresentationServiceImpl implements PresentationService {

	@Autowired
	ClientPresentationRepository cpRepository;

	@Autowired
	PresentationRepository repository;

	@Autowired
	UsuarioService userService;

	@Autowired
	PresentationDtoConverter converter;

	@Override
	public Presentation getById(Integer id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("No existe la unidad."));
	}

	@Override
	public List<PresentationDto> getPresentations() {
		Integer clientId = userService.getCurrentClient().getId();
		return converter.toDtoList(cpRepository.findByClientId(clientId));
	}

}
