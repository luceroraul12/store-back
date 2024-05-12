package distribuidora.scrapping.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.services.webscrapping.DonGasparWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.FacundoRenovadoWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;

/**
 * Servicio para realizar actualizaciones de buscador por web scrapping de
 * productos en base de datos.
 */
@Service
public class ActualizacionPorWebScrappingServicio {

	@Autowired
	private LaGranjaDelCentroWebScrappingServicio laGranjaDelCentroService;

	@Autowired
	private FacundoRenovadoWebScrappingServicio facundoService;

	@Autowired
	private DonGasparWebScrappingServicio donGasparService;

	/**
	 * Emite una distribuidora a la que se quiera actualizar. Condicion: que
	 * exista un bean de dicho servicio a actualizar.
	 * 
	 * @param distribuidoraCodigo
	 * @throws IOException
	 */
	public void update(String distribuidoraCodigo)
			throws IOException {
		switch (distribuidoraCodigo) {
			case Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO : {
				System.out.println("Actualiza la granja del centro");
				laGranjaDelCentroService
						.generarProductosEntidadYActualizarCollecciones(null);
				break;
			}
			case Constantes.LV_DISTRIBUIDORA_DON_GASPAR : {
				System.out.println("Actualiza don gaspar");
				donGasparService
						.generarProductosEntidadYActualizarCollecciones(null);
				break;
			}
			case Constantes.LV_DISTRIBUIDORA_FACUNDO : {
				System.out.println("Actualiza facundo");
				facundoService
						.generarProductosEntidadYActualizarCollecciones(null);
				break;
			}
			default :
				throw new IllegalArgumentException(
						"El codigo no pertenece a una distribuidora");
		}
	}
}
