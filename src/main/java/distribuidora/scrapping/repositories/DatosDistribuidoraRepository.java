package distribuidora.scrapping.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import distribuidora.scrapping.entities.DatosDistribuidora;

public interface DatosDistribuidoraRepository extends MongoRepository<DatosDistribuidora, String> {

    boolean existsByDistribuidoraCodigo(String distribuidoraCodigo);
    DatosDistribuidora findByDistribuidoraCodigo(String distribuidoraCodigo);

    void deleteByDistribuidoraCodigo(String distribuidoraCodigo);
}
