package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.ClientModule;

public interface ClientModuleRepository extends JpaRepository<ClientModule, Integer>{
	
	@Query("""
			SELECT cm 
			FROM ClientModule cm
			WHERE cm.client.id = :clientId
			""")
	List<ClientModule> findAll(Integer clientId);
}
