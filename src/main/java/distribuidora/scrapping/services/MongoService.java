package distribuidora.scrapping.services;

import java.io.IOException;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.PeticionWebScrapping;

public interface MongoService {
	
	DatosDistribuidora updatedistribuidoraExcel(PeticionExcel request) throws IOException;
	
	DatosDistribuidora updateDistribuidoraWebScrapping(PeticionWebScrapping request) throws IOException;

}

