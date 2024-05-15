package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;

/**
 * Servicio encargado de trabajar con base de datos y los datos de
 * distribuidora.
 */
@Service
public class DatoDistribuidoraServicio {

	@Autowired
	DatosDistribuidoraRepository repository;

	public DatosDistribuidora getByCode(String code) {
		return repository.getByCode(code);
	};

	public void actualizarDatos(DatosDistribuidora datos) {
		eliminarDatos(datos.getDistribuidora().getCodigo());
		repository.save(datos);
	}

	public void eliminarDatos(String distribuidoraCodigo) {
		repository.deleteByDistribuidoraCodigo(distribuidoraCodigo);
	}

	public boolean existsByDistribuidora(String distribuidoraCodigo) {
		return repository.existsByDistribuidoraCodigo(distribuidoraCodigo);
	}

	public void save(DatosDistribuidora data) {
		repository.save(data);
	}

	public DatosDistribuidora findByDistribuidoraCodigo(String code) {
		return repository.findByDistribuidoraCodigo(code);
	}
}
