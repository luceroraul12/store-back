package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto,Integer> {

    void deleteAllByDistribuidoraCodigo(String distribuidoraCodigo);

    Producto findByDistribuidoraCodigoAndId(String distribuidoraCodigo, String id);
}
