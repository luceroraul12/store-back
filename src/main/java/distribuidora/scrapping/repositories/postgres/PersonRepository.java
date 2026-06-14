package distribuidora.scrapping.repositories.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query("""
			SELECT p
			FROM Person p
			WHERE p.client.id = :clientId
				AND (:search IS NULL OR (
						UPPER(p.name) like UPPER(CONCAT('%', :search, '%'))
						OR p.phone like UPPER(CONCAT('%', :search, '%'))
					))
				""")
	Page<Person> findByClientId(Integer clientId, String search, Pageable pageable);

	@Query("""
			SELECT p 
			FROM Person p
				INNER JOIN p.client c
			WHERE c.id = :clientId
				AND p.phone = :phone
			""")
	Person findByPhoneAnClientId(String phone, Integer clientId);

}
