package distribuidora.scrapping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.entities.UpdateRequestExcel;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.services.ActualizacionPorWebScrappingServicio;
import distribuidora.scrapping.services.UpdaterService;
import distribuidora.scrapping.services.excel.ActualizacionPorDocumentoServicio;
import distribuidora.scrapping.services.internal.InventorySystem;

/**
 * Tiene la finalidad de realizar la actualizacion de datos por distribuidora
 * como tambien el estado de cada una.
 * 
 * @see ActualizacionPorDocumentoServicio
 */
@RestController
@RequestMapping(value = "/actualizar")
public class ActualizacionController {

	@Autowired
	ActualizacionPorDocumentoServicio actualizacionPorDocumentoServicio;

	@Autowired
	ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;

	@Autowired
	DatosDistribuidoraRepository datosDistribuidoraRepository;

	@Autowired
	InventorySystem inventorySystem;

	@Autowired
	UpdaterService updaterService;

	/**
	 * Permite actualizar los productos de las distribuidoras que dependan de
	 * excel. Actualiza una Distribuidora a la vez
	 * 
	 * @param documento
	 *            Debe contener todas sus partes.
	 * @throws Exception
	 * @see UpdateRequestExcel
	 */
	@PostMapping("update")
	public DatosDistribuidora update(UpdateRequest request) throws Exception {
		return updaterService.update(request);
	}

	/**
	 * Devuelve un map con el tipo de busqueda por distribuidora
	 * 
	 * @return map.excel dependen del excel, map.webScrapping dependen de Web
	 *         Scrapping map.webScrapping es un map de fecha Realizado y
	 *         Distribuidora
	 */
	@GetMapping
	public List<DatosDistribuidora> obtenerTipoyEstadoDeDistribuidora() {
		return inventorySystem.getDistribuidoraStatus();
	}

	@GetMapping("/eliminarIndices")
	public void eliminarIndices() {
		inventorySystem.eliminarIndices();
	}

}
