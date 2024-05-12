package distribuidora.scrapping.services.excel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.UpdateRequest;

/**
 * Servicio encargado de actualizaciones de base de dato del tipo excel.
 */
@Service
public class ActualizacionPorDocumentoServicio {

	@Autowired
	private VillaresExcelService villaresService;

	@Autowired
	private IndiasExcelService indiasService;

	public void update(UpdateRequest peticion) throws IOException {
		List<ProductSearcherExcel> services = Arrays.asList(villaresService,
				indiasService);
		switch (peticion.getDistribuidoraCodigo()) {
			case Constantes.LV_DISTRIBUIDORA_VILLARES : {
				System.out.println("Actualiza villares");
				villaresService.generarProductosEntidadYActualizarCollecciones(
						peticion);
				break;
			}

			case Constantes.LV_DISTRIBUIDORA_INDIAS : {
				System.out.println("Actualiza indias");
				indiasService.generarProductosEntidadYActualizarCollecciones(
						peticion);
				break;
			}
			default :
				throw new IllegalArgumentException(
						"El codigo no corresponde a una distribuidora");
		}
	}
}
