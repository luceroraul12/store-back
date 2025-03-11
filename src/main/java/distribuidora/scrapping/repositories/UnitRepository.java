package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer>{

	@Query("""
			SELECT u 
			FROM Unit u
			WHERE u.client.id = :clientId
				and u.selectable = :selectable
			""")
	List<Unit> findByClientId(Integer clientId, boolean selectable);

}
