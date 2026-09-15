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

	@Query("""
			SELECT COALESCE(SUM(cp.amount), 0.0)
			FROM CartPayment cp
				INNER JOIN cp.cart c
				INNER JOIN cp.paymentMethod lv
			WHERE c.client.id = :clientId
				AND lv.codigo = :paymentMethodCode
				AND c.dateCreated BETWEEN :dateFrom AND :dateTo
			""")
	Double sumAmountByClientIdAndPaymentMethodAndDateRange(Integer clientId, String paymentMethodCode,
			java.util.Date dateFrom, java.util.Date dateTo);
}
