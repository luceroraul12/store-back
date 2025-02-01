package distribuidora.scrapping.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

}
