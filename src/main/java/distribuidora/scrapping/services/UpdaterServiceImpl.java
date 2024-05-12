package distribuidora.scrapping.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.services.excel.ActualizacionPorDocumentoServicio;
import distribuidora.scrapping.services.excel.IndiasExcelService;
import distribuidora.scrapping.services.excel.VillaresExcelService;
import distribuidora.scrapping.services.webscrapping.DonGasparWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.FacundoRenovadoWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;

@Service
public class UpdaterServiceImpl implements UpdaterService {

	@Autowired
	ActualizacionPorDocumentoServicio actualizacionPorDocumentoServicio;

	@Autowired
	DatosDistribuidoraRepository datosDistribuidoraRepository;

	@Autowired
	ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;

	// Listado de servicios de implementaciones
	// EXCEL
	@Autowired
	private VillaresExcelService villaresService;

	@Autowired
	private IndiasExcelService indiasService;

	// WEB
	@Autowired
	private LaGranjaDelCentroWebScrappingServicio laGranjaDelCentroService;

	@Autowired
	private FacundoRenovadoWebScrappingServicio facundoService;

	@Autowired
	private DonGasparWebScrappingServicio donGasparService;

	@Override
	public DatosDistribuidora update(UpdateRequest request) throws Exception {
		// Genero un arreglo de los servicios actuales
		List<ProductSearcher> services = Arrays.asList(villaresService,
				indiasService, laGranjaDelCentroService, facundoService,
				donGasparService);

		// Busco el servicio por codigo que me estan pasando
		ProductSearcher service = services.stream()
				.filter(s -> s.getDistribuidoraCodigo()
						.equals(request.getDistribuidoraCodigo()))
				.findFirst().orElse(null);

		if (service == null) {
			throw new Exception("No existe dicha implementacion");
		}

		service.update(request);

		return datosDistribuidoraRepository
				.findByDistribuidoraCodigo(request.getDistribuidoraCodigo());
	}

}
