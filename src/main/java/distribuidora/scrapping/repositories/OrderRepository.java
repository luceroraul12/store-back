package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.Cart;

public interface OrderRepository extends JpaRepository<Cart, Integer>{

	@Query("""
		SELECT COUNT(c) > 0
		FROM Cart c
		WHERE c.customer.id = :id
		""")
	boolean hasCartByCustomerId(Integer id);

}
