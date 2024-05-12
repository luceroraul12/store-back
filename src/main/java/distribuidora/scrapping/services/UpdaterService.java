package distribuidora.scrapping.services;

import java.io.IOException;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.UpdateRequest;

public interface UpdaterService {

	DatosDistribuidora update(UpdateRequest request)
			throws IOException, Exception;

}
