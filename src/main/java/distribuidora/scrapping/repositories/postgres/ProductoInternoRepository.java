package distribuidora.scrapping.repositories.postgres;

import distribuidora.scrapping.entities.ProductoInterno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoInternoRepository extends JpaRepository<ProductoInterno, Integer> {}
