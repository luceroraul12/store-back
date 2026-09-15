package distribuidora.scrapping.repositories;

import java.util.Optional;

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
}
