package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

	@Query("""
			select s
			from Supplier s
			where s.client.id = :clientId
			order by s.name
			""")
	List<Supplier> findByClientId(Integer clientId);

	@Query("""
			select s
			from Supplier s
			where s.id = :id and s.client.id = :clientId
			""")
	Supplier findByIdAndClientId(Integer id, Integer clientId);
}
