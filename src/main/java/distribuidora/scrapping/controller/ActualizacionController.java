package distribuidora.scrapping.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.services.ActualizacionPorWebScrappingServicio;
import distribuidora.scrapping.services.MongoService;
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
	MongoService mongoService;

	/**
	 * Permite actualizar los productos de las distribuidoras que dependan de
	 * excel. Actualiza una Distribuidora a la vez
	 * 
	 * @param documento
	 *            Debe contener todas sus partes.
	 * @see PeticionExcel
	 * @throws IOException
	 */
	@PostMapping("excel")
	public ResponseEntity<DatosDistribuidora> actualizacionPorExcel(
			PeticionExcel documento) throws IOException {
		return new ResponseEntity<>(
				mongoService.updatedistribuidoraExcel(documento),
				HttpStatus.OK);
	}

	/**
	 * Permite actualizar los productos de las distribuidoras que dependan de
	 * Web Scrapping. Actualiza todas las distribuidoras de este tipo al mismo
	 * tiempo.
	 */
	@GetMapping("web-scrapping")
	public void actualizacionPorWebScrappingTodos() {
		this.actualizacionPorWebScrappingServicio
				.actualizarTodasLasDistribuidoras();
	}

	@PostMapping("web-scrapping/individual")
	public ResponseEntity<DatosDistribuidora> actualizacionPorWebScrappingIndividual(
			@RequestParam String distribuidoraCodigo) {
		return new ResponseEntity<>(
				mongoService.updateDistribuidoraWebScrapping(
						new PeticionWebScrapping(distribuidoraCodigo)),
				HttpStatus.OK);
	}

	// TODO: este metodo de momento va aquedar fijo aca, pero en un futuro
	// habria que crear un servicio o almacenar este dato en algun lado

	/**
	 * Devuelve un map con el tipo de busqueda por distribuidora
	 * 
	 * @return map.excel dependen del excel, map.webScrapping dependen de Web
	 *         Scrapping map.webScrapping es un map de fecha Realizado y
	 *         Distribuidora
	 */
	@GetMapping
	public ResponseEntity<List<DatosDistribuidora>> obtenerTipoyEstadoDeDistribuidora() {
		return new ResponseEntity<>(inventorySystem.getDistribuidoraStatus(),
				HttpStatus.OK);
	}

}
