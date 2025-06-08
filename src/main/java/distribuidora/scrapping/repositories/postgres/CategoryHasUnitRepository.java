package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Category;

public interface CategoryHasUnitRepository extends JpaRepository<Category, Integer> {
	@Query("""
			SELECT c
			FROM Category c
			where c.client.id = :clientId
			""")
	List<Category> findCategoriesByClientId(Integer clientId);
}
