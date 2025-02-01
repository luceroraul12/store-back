package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.repositories.postgres.PersonRepository;
import distribuidora.scrapping.util.converters.PersonDtoConverter;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	PersonDtoConverter personDtoConverter;
	
	@Autowired
	PersonRepository personRepository;

	@Override
	public Integer createUpdatePerson(PersonDto dto) {
		Person person = personDtoConverter.toEntidad(dto);
		person = personRepository.save(person);
		return person.getId();
	}

	@Override
	public List<PersonDto> getPersons() {
		List<Person> persons = personRepository.findAll();
		return personDtoConverter.toDtoList(persons);
	}

	@Override
	public Integer deletePerson(Integer id) {
		personRepository.deleteById(id);
		return id;
	}
	
	
	
	

}
