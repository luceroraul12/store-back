package distribuidora.scrapping.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.customer.CashRegisterSession;

public interface CashRegisterSessionRepository extends JpaRepository<CashRegisterSession, Integer> {

	@Query("""
			SELECT crs
			FROM CashRegisterSession crs
			WHERE crs.client.id = :clientId
				AND crs.status = 'OPEN'
			""")
	Optional<CashRegisterSession> findOpenSessionByClientId(Integer clientId);

	@Query("""
			SELECT crs
			FROM CashRegisterSession crs
			WHERE crs.client.id = :clientId
			AND crs.status = 'CLOSED'
			ORDER BY crs.closingDate DESC
			""")
	Page<CashRegisterSession> findLastCloseSession(Integer clientId, Pageable pageable);

	@Query("""
			SELECT crs
			FROM CashRegisterSession crs
			WHERE crs.client.id = :clientId
				AND crs.openingDate BETWEEN :dateFrom AND :dateTo
			ORDER BY crs.openingDate DESC
			""")
	Page<CashRegisterSession> findByClientIdAndDateRange(Integer clientId, Date dateFrom, Date dateTo,
			Pageable pageable);

}
