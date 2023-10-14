package distribuidora.scrapping.services.excel;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.PeticionExcel;

/**
 * Servicio encargado de actualizaciones de base de dato del tipo excel.
 */
@Service
public class ActualizacionPorDocumentoServicio {
	
	@Autowired
	private VillaresExcelServicio villaresService;
	
	@Autowired
	private IndiasExcelServicio indiasService;

    public void recibirDocumento(PeticionExcel peticion) throws IOException {
    	switch (peticion.getDistribuidoraCodigo()){
    		case Constantes.LV_DISTRIBUIDORA_VILLARES : {
    			System.out.println("Actualiza villares");
				villaresService.generarProductosEntidadYActualizarCollecciones(peticion);
				break;
			}
    		
    		case Constantes.LV_DISTRIBUIDORA_INDIAS : {
    			System.out.println("Actualiza indias");
				indiasService.generarProductosEntidadYActualizarCollecciones(peticion);
				break;
			}
			default :
				throw new IllegalArgumentException("El codigo no corresponde a una distribuidora");
		}
    }
}
