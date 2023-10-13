package distribuidora.scrapping.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.ProductoInternoStatus;

public interface ProductoInternoStatusRepository extends JpaRepository<ProductoInternoStatus, Integer> {

}
