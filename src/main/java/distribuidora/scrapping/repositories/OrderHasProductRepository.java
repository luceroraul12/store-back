package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.OrderHasProduct;

public interface OrderHasProductRepository extends JpaRepository<OrderHasProduct, Integer>{

	@Query("""
			SELECT ohp 
			FROM OrderHasProduct ohp 
				INNER JOIN ohp.order o 
				INNER JOIN o.customer cus
				INNER JOIN o.client cli
			WHERE cus.username = :username
				AND cli.name = :storeCode
			""")
	List<OrderHasProduct> findByStoreCodeAndUsername(String storeCode,
			String username);

	@Query("""
			SELECT ohp 
			FROM OrderHasProduct ohp
				INNER JOIN ohp.order o
				INNER JOIN ohp.product
			WHERE o.id in :orderIds
			""")
	List<OrderHasProduct> findAllByOrderId(Integer... orderIds);

}
