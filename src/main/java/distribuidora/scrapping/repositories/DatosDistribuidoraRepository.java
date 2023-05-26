package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.DatosDistribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatosDistribuidoraRepository extends MongoRepository<DatosDistribuidora, String> {

    boolean existsByDistribuidoraCodigo(String distribuidoraCodigo);
    DatosDistribuidora findByDistribuidoraCodigo(String distribuidoraCodigo);

    void deleteByDistribuidoraCodigo(String distribuidoraCodigo);
}
