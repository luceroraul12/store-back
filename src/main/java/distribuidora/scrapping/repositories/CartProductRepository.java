package distribuidora.scrapping.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import distribuidora.scrapping.entities.customer.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{

	List<CartProduct> findByClientId(Integer id);

}
