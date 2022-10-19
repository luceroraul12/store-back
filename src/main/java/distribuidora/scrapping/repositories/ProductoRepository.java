package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto,Integer> {

    void deleteAllByDistribuidora(Distribuidora distribuidora);
}
