package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;

public interface PersonService {

	Integer createUpdatePerson(PersonDto dto);

	List<PersonDto>  getPersons(String search);

	Integer deletePerson(Integer id) throws Exception;

	Person getById(Integer id);

}
