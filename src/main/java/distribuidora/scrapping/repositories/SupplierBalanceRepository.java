package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.SupplierBalance;

public interface SupplierBalanceRepository extends JpaRepository<SupplierBalance, Integer> {

	@Query("""
			select b
			from SupplierBalance b
			where b.supplier.id = :supplierId
			and b.supplier.client.id = :clientId
			order by b.createdAt desc
			""")
	List<SupplierBalance> findBySupplierIdAndClientId(Integer supplierId, Integer clientId);

	@Query("""
			select b
			from SupplierBalance b
			where b.id = :id
			and b.supplier.id = :supplierId
			and b.supplier.client.id = :clientId
			""")
	SupplierBalance findByIdAndSupplierIdAndClientId(Integer id, Integer supplierId, Integer clientId);
}
