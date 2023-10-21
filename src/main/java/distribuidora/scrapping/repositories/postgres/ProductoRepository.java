package distribuidora.scrapping.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.ExternalProduct;

public interface ProductoRepository extends JpaRepository<ExternalProduct, Integer> {

//    void deleteAllByDistribuidoraCodigo(String distribuidoraCodigo);
//
//	ExternalProduct findByDistribuidoraCodigoAndId(String distribuidoraCodigo,
//			String id);
//
//	List<ExternalProduct> findByDistribuidoraCodigo(String distribuidoraCodigo);
}
