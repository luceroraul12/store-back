package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.ProductoEspecifico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoEspecificoRepository<Entidad extends ProductoEspecifico>
        extends MongoRepository<Entidad, String> {
}
