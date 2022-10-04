package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entidad.UnionEntidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnionRepository extends MongoRepository<UnionEntidad,String> {
}
