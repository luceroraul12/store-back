package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.CartProduct;

public interface CartProductRepository
		extends
			JpaRepository<CartProduct, Integer> {

	@Query("""
			SELECT cp
			FROM CartProduct cp
				INNER JOIN cp.cart c
			WHERE c.client.id = :clientId
			""")
	List<CartProduct> findByClientId(Integer clientId);

}
