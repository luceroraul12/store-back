package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ClientDto;
import distribuidora.scrapping.entities.Client;

@Component
public class ClientDtoConverter extends Converter<Client, ClientDto>{

	@Override
	public ClientDto toDto(Client e) {
		ClientDto dto = new ClientDto();
		dto.setId(e.getId());
		dto.setAddress(e.getAddress());
		dto.setAddressLink(e.getAddressLink());
		dto.setFacebook(e.getFacebook());
		dto.setFacebookLink(e.getFacebookLink());
		dto.setInstagram(e.getInstagram());
		dto.setInstagramLink(e.getInstagramLink());
		dto.setName(e.getName());
		dto.setPhone(e.getPhone());
		dto.setPhoneLink(e.getPhoneLink());
		return dto;
	}

	@Override
	public Client toEntidad(ClientDto d) {
		// TODO Auto-generated method stub
		return null;
	}

}
