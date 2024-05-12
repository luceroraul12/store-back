package distribuidora.scrapping.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.services.excel.ActualizacionPorDocumentoServicio;

@Service
public class MongoServiceImpl implements UpdaterService {
	
	@Autowired
    ActualizacionPorDocumentoServicio actualizacionPorDocumentoServicio;
	
	@Autowired
    DatosDistribuidoraRepository datosDistribuidoraRepository;
	
	@Autowired
	ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;
	

	@Override
	public DatosDistribuidora updateByExcel(PeticionExcel request) throws IOException {
		actualizacionPorDocumentoServicio.recibirDocumento(request);
        DatosDistribuidora resultado = this.datosDistribuidoraRepository.findByDistribuidoraCodigo(request.getDistribuidoraCodigo());
		return resultado;
	}


	@Override
	public DatosDistribuidora updateByWeb(
			PeticionWebScrapping request) throws IOException {
		actualizacionPorWebScrappingServicio.actualizarPorDistribuidora(request.getDistribuidoraCodigo());
        DatosDistribuidora resultado = this.datosDistribuidoraRepository.findByDistribuidoraCodigo(request.getDistribuidoraCodigo());
		return resultado;
	}

}
