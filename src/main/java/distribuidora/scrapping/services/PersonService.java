package distribuidora.scrapping.services;

import org.springframework.data.domain.Page;

import distribuidora.scrapping.dto.CustomerDto;
import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;

public interface PersonService {

	Integer createUpdatePerson(PersonDto dto);

	Page<PersonDto> getPersons(String search, Integer page, Integer size);

	Integer deletePerson(Integer id) throws Exception;

	Person getById(Integer id);

	CustomerDto checkCustomer(String phone, Integer clientId) throws Exception;

}
