package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.Client;

public interface ClientDataRepository extends JpaRepository<Client, Integer>{
	
}
