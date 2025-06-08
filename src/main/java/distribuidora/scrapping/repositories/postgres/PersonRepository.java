package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

	@Query("""
		SELECT p
		FROM Person p
		WHERE p.client.id = :clientId
			AND (:search IS NULL OR (
					UPPER(p.name) like UPPER(CONCAT('%', :search, '%'))
					OR p.phone like UPPER(CONCAT('%', :search, '%'))
				))
			""")
	List<Person> findByClientId(Integer clientId, String search);

}
