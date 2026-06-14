package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.ProductoInterno;

public interface ProductoInternoRepository extends JpaRepository<ProductoInterno, Integer> {
	
	@Query("""
			SELECT pi 
			FROM ProductoInterno pi 
			WHERE pi.externalProduct IS NOT NULL
				AND pi.status = 'ACTIVE'
			""")
	List<ProductoInterno> getProductosReferenciados();
	
	
	@Query("""
			SELECT pi 
			FROM ProductoInterno pi 	
			WHERE pi.id IN (:productoInternoIds)
				AND pi.status = 'ACTIVE'
				""")
	List<ProductoInterno> getProductsByIds(@Param("productoInternoIds") List<Integer> productoInternoIds);

	public final String getAllProductosByUserIdAndSearch = """
		    SELECT pi
		    FROM ProductoInterno pi
		        INNER JOIN pi.category cat
		        INNER JOIN pi.client c
		    WHERE c.id = :clientId
		        AND (
		        	(:search is null) OR
		        	  (
		             UPPER(pi.nombre) LIKE UPPER(CONCAT('%', :search, '%'))
		             OR UPPER(pi.descripcion) LIKE UPPER(CONCAT('%', :search, '%'))
		             OR UPPER(pi.category.name) LIKE UPPER(CONCAT('%', :search, '%'))
		         	)
		        )
			    AND pi.status = 'ACTIVE'
		    ORDER BY cat.name, pi.nombre, pi.descripcion
		""";
	
	@Query(getAllProductosByUserIdAndSearch)
	List<ProductoInterno> getAllProductosByUserIdAndSearch(Integer clientId, String search);
	
	@Query(getAllProductosByUserIdAndSearch)
	Page<ProductoInterno> getPageAllProductosByUserIdAndSearch(Integer clientId, String search, Pageable pageable);

	@Query("""
			SELECT COUNT(*)
			FROM ProductoInterno pi
			WHERE pi.id IN (:productIds)
				AND pi.status = 'ACTIVE'
			""")
	int countProductsByIds(@Param("productIds") List<Integer> productIds);

	@Query("""
			SELECT count(p) > 0
	 		FROM ProductoInterno p
	 		WHERE p.category.id = :categoryId
				AND p.status = 'ACTIVE'
			 		""")
	boolean hasProductWithCategoryId(Integer categoryId);
}
