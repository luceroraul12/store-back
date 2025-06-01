package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.ClientPresentation;
import distribuidora.scrapping.entities.Presentation;

public interface ClientPresentationRepository extends JpaRepository<ClientPresentation, Integer>{
	
	@Query("""
			select cp.presentation
			from ClientPresentation cp
			where cp.client.id = :clientId
				and cp.selectable = True
			""")
	List<Presentation> findByClientId(Integer clientId);

}
