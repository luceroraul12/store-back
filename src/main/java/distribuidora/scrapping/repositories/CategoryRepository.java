package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("""
			SELECT c
			FROM Category c
			WHERE c.client.id = :clientId
			""")
	List<Category> findCategoriesByClientId(Integer clientId);

	@Query("""
			SELECT c
			FROM Category c
			WHERE c.client.id = :clientId
				and UPPER(c.name) = UPPER(:name)
			""")
	Category findCategoryByNameAndClientId(String name, Integer clientId);

}
