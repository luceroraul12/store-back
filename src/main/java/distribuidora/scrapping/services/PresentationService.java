package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.PresentationDto;
import distribuidora.scrapping.entities.Presentation;

public interface PresentationService {

	Presentation getById(Integer id) throws Exception;

	List<PresentationDto> getPresentations();
}
