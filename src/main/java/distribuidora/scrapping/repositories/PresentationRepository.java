package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Integer>{

}
