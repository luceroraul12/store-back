package distribuidora.scrapping.repositories.postgres;

import distribuidora.scrapping.entities.ProductoInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoInternoRepository extends JpaRepository<ProductoInterno, Integer> {

    @Query("SELECT pi FROM ProductoInterno pi " +
            "   INNER JOIN pi.distribuidoraReferencia d " +
            "WHERE pi.codigoReferencia IS NOT NULL " +
            "   AND d IS NOT NULL ")
    List<ProductoInterno> getProductosReferenciados();
}
