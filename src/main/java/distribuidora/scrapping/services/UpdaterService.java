package distribuidora.scrapping.services;

import java.io.IOException;

import distribuidora.scrapping.dto.DatosDistribuidoraDto;
import distribuidora.scrapping.entities.UpdateRequest;

public interface UpdaterService {

	DatosDistribuidoraDto update(UpdateRequest request)
			throws IOException, Exception;

}
