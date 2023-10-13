package distribuidora.scrapping.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import distribuidora.scrapping.entities.ProductoEspecifico;

public interface ProductoEspecificoRepository<Entidad extends ProductoEspecifico>
        extends MongoRepository<Entidad, String> {

    void deleteAllByDistribuidora(String distribuidoraCodigo);
}
