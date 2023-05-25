package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de trabajar con base de datos y los datos de distribuidora.
 */
@Service
public class DatoDistribuidoraServicio implements AccionesBaseDeDatos<DatosDistribuidora> {

    @Autowired
    DatosDistribuidoraRepository repository;

    @Override
    public void actualizarDatos(List<DatosDistribuidora> datos) {
        String distribuidoraCodigo = datos.get(0).getDistribuidoraCodigo();
        eliminarDatos(distribuidoraCodigo);
        guardarDatos(datos);
    }

    @Override
    public void guardarDatos(List<DatosDistribuidora> datos) {
        repository.saveAll(datos);
    }

    @Override
    public void eliminarDatos(String distribuidoraCodigo) {
        repository.deleteByDistribuidora(distribuidoraCodigo);
    }

    public boolean existsByDistribuidora(String distribuidoraCodigo) {
        return repository.existsByDistribuidoraCodigo(distribuidoraCodigo);
    }
}
