package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.ProductoInterno;

public interface ProductoInternoRepository extends JpaRepository<ProductoInterno, Integer> {

    @Query("SELECT pi FROM ProductoInterno pi " +
            "WHERE pi.externalProduct IS NOT NULL")
    List<ProductoInterno> getProductosReferenciados();

    @Query("SELECT pi FROM ProductoInterno pi " +
            "WHERE pi.id IN (:productoInternoIds)")
    List<ProductoInterno> getProductosPorIds(@Param("productoInternoIds") List<Integer> productoInternoIds);

    @Query("SELECT pi FROM ProductoInterno pi " +
            "   LEFT JOIN pi.lvCategoria c " +
            "ORDER BY c.descripcion, pi.nombre, pi.descripcion")
    List<ProductoInterno> getAllProductos();
}
