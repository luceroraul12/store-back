package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoEspecificoRepository<Entidad extends ProductoEspecifico>
        extends MongoRepository<Entidad, String> {

    void deleteAllByDistribuidora(Distribuidora distribuidora);
}
