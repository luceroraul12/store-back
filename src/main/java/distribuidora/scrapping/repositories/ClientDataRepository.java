package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.Client;

public interface ClientDataRepository extends JpaRepository<Client, Integer>{

	@Query("""
			SELECT c 
			FROM Client c 
			WHERE c.name = :code
			""")
	Client findByCode(@Param("code") String code);
}
