package distribuidora.scrapping.services;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.repositories.postgres.PersonRepository;
import distribuidora.scrapping.util.converters.PersonDtoConverter;

@Service
public class PersonServiceImpl implements PersonService {

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
	public Page<PersonDto> getPersons(String search, Integer page, Integer size) {
		if (StringUtils.isEmpty(search))
			search = null;
		Pageable pageable = PageRequest.of(page, size);
		Page<Person> pageData = personRepository.findByClientId(userService.getCurrentClient().getId(), search,
				pageable);
		return personDtoConverter.toPage(pageData);
	}

	@Override
	public Integer deletePerson(Integer id) throws Exception {
		// Si la persona tiene pedidos no puedo eliminarlo
		if (cartService.hasCartByCustomerId(id)) {
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
