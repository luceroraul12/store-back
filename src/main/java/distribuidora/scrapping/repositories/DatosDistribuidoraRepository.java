package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatosDistribuidoraRepository extends MongoRepository<DatosDistribuidora, String> {

    boolean existsByDistribuidora(Distribuidora distribuidora);
}
