package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.PersonDto;
import distribuidora.scrapping.entities.Person;

@Component
public class PersonDtoConverter extends Converter<Person, PersonDto> {

	@Override
	public PersonDto toDto(Person entidad) {
		PersonDto dto = new PersonDto();
		dto.setId(entidad.getId());
		dto.setName(entidad.getName());
		dto.setPhone(entidad.getPhone());
		dto.setEmail(entidad.getEmail());
		return dto;
	}

	@Override
	public Person toEntidad(PersonDto dto) {
		Person entidad = new Person();
		entidad.setId(dto.getId());
		entidad.setName(dto.getName());
		entidad.setPhone(dto.getPhone());
		entidad.setEmail(dto.getEmail());
		return entidad;
	}

}
