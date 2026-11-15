package distribuidora.scrapping.repositories.postgres;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
	List<Expense> findByClientId(Integer clientId);

	@Query("""
			SELECT e FROM Expense e
			WHERE e.client.id = :clientId
				AND e.creationDate BETWEEN :dateFrom AND :dateTo
			ORDER BY e.creationDate DESC
			""")
	Page<Expense> findPageByParams(Integer clientId, Date dateFrom, Date dateTo, Pageable pageable);

	@Query("""
			SELECT e FROM Expense e
			WHERE e.client.id = :clientId
				AND (:hasDateFrom = false OR e.creationDate >= :dateFrom)
				AND (:hasDateTo = false OR e.creationDate <= :dateTo)
			ORDER BY e.creationDate DESC
			""")
	List<Expense> findByClientIdAndDateRange(Integer clientId, boolean hasDateFrom, Date dateFrom, boolean hasDateTo,
			Date dateTo);

	Expense findByIdAndClientId(Integer id, Integer clientId);
}
