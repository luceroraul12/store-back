package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.ExternalProduct;

public interface ExternalProductRepository extends JpaRepository<ExternalProduct, Integer> {

	@Query("SELECT ep "
			+ "FROM ExternalProduct ep "
			+ "	INNER JOIN ep.distribuidora d "
			+ "WHERE d.codigo = :distribuidoraCode "
			+ "	AND (:productCode IS NULL OR :productCode = ep.code)")
	List<ExternalProduct> findByDistribuidoraCodeAndProductCode(
			@Param("distribuidoraCode") String distribuidoraCode,
			@Param("productCode") String productCode);
	
	@Query("SELECT ep "
			+ "FROM ExternalProduct ep "
			+ "WHERE ep.title LIKE %:search% ")
	List<ExternalProduct> findBySearch(@Param("search") String search);
}
