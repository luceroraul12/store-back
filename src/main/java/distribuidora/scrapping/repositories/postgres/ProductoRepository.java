package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.ExternalProduct;

public interface ProductoRepository extends JpaRepository<ExternalProduct, Integer> {


	List<ExternalProduct> findByDistribuidoraCodeAndProductCode(String distribuidoraCodigo,
			String id);
}
