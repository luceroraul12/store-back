package distribuidora.scrapping.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.repositories.postgres.PersonRepository;
import distribuidora.scrapping.security.handler.CustomException;
import distribuidora.scrapping.util.converters.PersonDtoConverter;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	PersonDtoConverter personDtoConverter;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UsuarioService userService;

	@Override
	public Integer createUpdatePerson(PersonDto dto) {
		Person person = personDtoConverter.toEntidad(dto);
		person.setClient(userService.getCurrentClient());
		person = personRepository.save(person);
		return person.getId();
	}

	@Override
	public List<PersonDto> getPersons() {
		List<Person> persons = personRepository.findByClientId(userService.getCurrentClient().getId());
		return personDtoConverter.toDtoList(persons);
	}

	@Override
	public Integer deletePerson(Integer id) throws Exception {
		// Si la persona tiene pedidos no puedo eliminarlo
		if(cartService.hasCartByCustomerId(id)) {
			throw new Exception("La persona cuenta con pedidos asociados.");
		}
		personRepository.deleteById(id);
		return id;
	}

	@Override
	public Person getById(Integer id) {
		Optional<Person> result = personRepository.findById(id);
		return result.isEmpty() ? null : result.get();
	}
	

}
