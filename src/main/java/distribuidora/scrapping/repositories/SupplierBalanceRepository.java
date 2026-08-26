package distribuidora.scrapping.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	Page<SupplierBalance> findBySupplierIdAndClientId(Integer supplierId, Integer clientId, Pageable pageable);

	@Query("""
			select case when count(b) > 0 then true else false end
			from SupplierBalance b
			where b.supplier.id = :supplierId
			and b.supplier.client.id = :clientId
			""")
	boolean existsBySupplierIdAndClientId(Integer supplierId, Integer clientId);

	@Query("""
			select coalesce(sum(b.amount), 0)
			from SupplierBalance b
			where b.supplier.id = :supplierId
			and b.supplier.client.id = :clientId
			and b.balanceType.codigo = :typeCode
			""")
	Double sumByType(Integer supplierId, Integer clientId, String typeCode);

	@Query("""
			select b
			from SupplierBalance b
			where b.id = :id
			and b.supplier.id = :supplierId
			and b.supplier.client.id = :clientId
			""")
	SupplierBalance findByIdAndSupplierIdAndClientId(Integer id, Integer supplierId, Integer clientId);
}
