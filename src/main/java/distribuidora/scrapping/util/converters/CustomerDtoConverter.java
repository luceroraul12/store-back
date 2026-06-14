package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CustomerDto;
import distribuidora.scrapping.entities.Person;

@Component
public class CustomerDtoConverter extends Converter<Person, CustomerDto> {
	
	@Autowired
	ClientDtoConverter clientDtoConverter;

	@Override
	public CustomerDto toDto(Person e) {
		CustomerDto dto = null;
		if(e != null) {
			dto = new CustomerDto();
			dto.setId(e.getId());
			dto.setName(e.getName());
			dto.setPhone(e.getPhone());
			if(e.getClient() != null)
				dto.setClient(clientDtoConverter.toDto(e.getClient()));
		}
		return dto;
	}

	@Override
	public Person toEntidad(CustomerDto d) {
		// TODO Auto-generated method stub
		return null;
	}

}
