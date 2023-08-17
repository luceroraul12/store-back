package distribuidora.scrapping.repositories.postgres;

import distribuidora.scrapping.entities.ProductoInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoInternoRepository extends JpaRepository<ProductoInterno, Integer> {

    @Query("SELECT pi FROM ProductoInterno pi " +
            "   INNER JOIN pi.distribuidoraReferencia d " +
            "WHERE pi.codigoReferencia IS NOT NULL " +
            "   AND d IS NOT NULL ")
    List<ProductoInterno> getProductosReferenciados();

    @Query("SELECT pi FROM ProductoInterno pi " +
            "WHERE pi.id IN (:productoInternoIds)")
    List<ProductoInterno> getProductosPorIds(@Param("productoInternoIds") List<Integer> productoInternoIds);

    @Query("SELECT pi FROM ProductoInterno pi " +
            "   LEFT JOIN pi.lvCategoria c " +
            "ORDER BY c.descripcion, pi.nombre, pi.descripcion")
    List<ProductoInterno> getAllProductos();
}
