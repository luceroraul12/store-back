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
				and (:typeCode IS NULL OR c.categoryType.codigo = :typeCode)
			ORDER BY c.name
			""")
	List<Category> findCategoriesByClientIdAndType(Integer clientId, String typeCode);

	@Query("""
			SELECT c
			FROM Category c
			WHERE c.client.id = :clientId
				and c.categoryType.codigo = :typeCode
				and UPPER(c.name) = UPPER(:name)
			""")
	Category findCategoryByNameAndClientIdAndType(String name, Integer clientId, String typeCode);

}
