package distribuidora.scrapping.services;

import java.io.IOException;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.PeticionWebScrapping;

public interface UpdaterService {
	
	DatosDistribuidora updateByExcel(PeticionExcel request) throws IOException;
	
	DatosDistribuidora updateByWeb(PeticionWebScrapping request) throws IOException;

}

