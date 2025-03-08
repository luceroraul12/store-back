package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.LookupParentChild;

public interface LookupParentChildRepository extends JpaRepository<LookupParentChild, Integer>{

	@Query("""
			SELECT r 
			FROM LookupParentChild r
			WHERE r.parent.id IN :parentIds
			""")
	List<LookupParentChild> findByParentIds(List<Integer> parentIds);
}
