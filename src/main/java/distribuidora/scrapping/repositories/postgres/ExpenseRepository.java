package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
	List<Expense> findByClientId(Integer clientId);

	Expense findByIdAndClientId(Integer id, Integer clientId);
}
