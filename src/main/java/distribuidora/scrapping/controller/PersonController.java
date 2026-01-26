package distribuidora.scrapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.services.PersonService;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping
	Integer createUpdatePerson(@RequestBody PersonDto dto) {
		return personService.createUpdatePerson(dto);
	}

	@GetMapping
	Page<PersonDto> getPersons(@RequestParam(required = false) String search,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return personService.getPersons(search, page, size);
	}

	@DeleteMapping("/{id}")
	Integer deletePerson(@PathVariable Integer id) throws Exception {
		return personService.deletePerson(id);
	}
}
