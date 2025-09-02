package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

	@Query("""
			select d
			from Discount d 
			where d.client.id = :clientId
			""")
	List<Discount> findByClientId(Integer clientId);

}
