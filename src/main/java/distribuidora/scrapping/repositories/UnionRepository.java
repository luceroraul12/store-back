package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnionRepository<Entidad extends ProductoEspecifico> extends MongoRepository<UnionEntidad<Entidad>,String> {

    UnionEntidad<Entidad> findByDistribuidora(@Param("distribuidora") Distribuidora distribuidora);

    String deleteUnionEntidadByDistribuidora(@Param("distribuidora") Distribuidora distribuidora);

}
