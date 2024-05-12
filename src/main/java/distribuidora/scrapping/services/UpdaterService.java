package distribuidora.scrapping.services;

import java.io.IOException;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.UpdateRequestExcel;
import distribuidora.scrapping.entities.UpdateRequestWeb;

public interface UpdaterService {
	
	DatosDistribuidora updateByExcel(UpdateRequestExcel request) throws IOException;
	
	DatosDistribuidora updateByWeb(UpdateRequestWeb request) throws IOException;

}

