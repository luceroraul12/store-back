package distribuidora.scrapping.repositories.postgres;

import distribuidora.scrapping.entities.ProductoInternoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoInternoStatusRepository extends JpaRepository<ProductoInternoStatus, Integer> {

}
