package distribuidora.scrapping.repositories;

import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnionRepository<Entidad> extends MongoRepository<UnionEntidad<Entidad>,String> {

    @Query("Select u from UnionEntidad where u.distribuidora = :distribuidora")
    UnionEntidad<Entidad> obtenerProductos(@Param("distribuidora") Distribuidora distribuidora);

}
