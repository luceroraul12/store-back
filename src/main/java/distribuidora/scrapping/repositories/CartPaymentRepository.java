package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.CartPayment;

public interface CartPaymentRepository extends JpaRepository<CartPayment, Integer> {

	@Query("""
			SELECT cp
			FROM CartPayment cp
			WHERE cp.cart.id IN :cartIds
			""")
	List<CartPayment> findByCartIds(List<Integer> cartIds);

	void deleteByCartId(Integer cartId);
}
