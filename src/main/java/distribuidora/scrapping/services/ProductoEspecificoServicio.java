package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.ProductoEspecifico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoEspecificoServicio<Entidad extends ProductoEspecifico> implements AccionesBaseDeDatos<Entidad> {

    @Autowired
    private MongoRepository<Entidad, String> repository;

    @Override
    public void actualizarDatos(List<Entidad> datos) {
        eliminarDatos();
        guardarDatos(datos);
    }

    @Override
    public void guardarDatos(List<Entidad> datos) {
        repository.saveAll(datos);
    }

    @Override
    public void eliminarDatos() {
        repository.deleteAll();
    }
}
