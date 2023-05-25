package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.repositories.ProductoEspecificoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio destinado a trabajar con la base de datos y los productos especificos.<br>
 * Para este caso, opte por almacenar todos los productos de las diferentes distribuidoras en la misma coleccion.<br>
 * Esto lo hice de esa manera por la libertad que brinda NoSQL con MongoDB.
 * @param <Entidad>
 */
@Service
public class ProductoEspecificoServicio<Entidad extends ProductoEspecifico> implements AccionesBaseDeDatos<Entidad> {

    @Autowired
    private ProductoEspecificoRepository<Entidad> repository;

    @Override
    public void actualizarDatos(List<Entidad> datos) {
        String distribuidoraCodigo = datos.get(0).getDistribuidora();
        eliminarDatos(distribuidoraCodigo);
        guardarDatos(datos);
    }

    @Override
    public void guardarDatos(List<Entidad> datos) {
        repository.saveAll(datos);
    }

    @Override
    public void eliminarDatos(String distribuidoraCodigo) {
        repository.deleteAllByDistribuidora(distribuidoraCodigo);
    }
}
