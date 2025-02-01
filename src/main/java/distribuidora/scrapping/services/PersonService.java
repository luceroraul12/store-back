package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.PersonDto;

public interface PersonService {

	Integer createUpdatePerson(PersonDto dto);

	List<PersonDto>  getPersons();

	Integer deletePerson(Integer id);

}
