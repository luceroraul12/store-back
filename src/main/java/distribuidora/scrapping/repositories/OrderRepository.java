package distribuidora.scrapping.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.Cart;

public interface OrderRepository extends JpaRepository<Cart, Integer> {

	@Query("""
			SELECT COUNT(c) > 0
			FROM Cart c
			WHERE c.customer.id = :id
			""")
	boolean hasCartByCustomerId(Integer id);

	@Query("""
			SELECT COUNT(c) > 0
			FROM Cart c
			WHERE c.discount.id = :id
			""")
	boolean hasCartsByDiscountId(Integer id);

	@Query("""
			SELECT c
			FROM Cart c
			WHERE c.client.id = :clientId
				AND (:personId IS NULL OR c.customer.id = :personId)
				AND c.dateCreated BETWEEN :dateFrom AND :dateTo
			ORDER BY c.dateCreated DESC
			""")
	Page<Cart> findPageByClientIdAndPersonId(Integer clientId, Integer personId,Date dateFrom, Date dateTo, Pageable pageable);

	@Query("""
			SELECT c
			FROM Cart c
			WHERE c.client.id = :clientId
				AND c.dateCreated BETWEEN :df AND :dt
			ORDER BY c.dateCreated ASC
			""")
	List<Cart> getCartsByClientAndDates(Integer clientId, Date df, Date dt);

}
