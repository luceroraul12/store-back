package distribuidora.scrapping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.ClientDto;
import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ClientModule;
import distribuidora.scrapping.repositories.ClientHasUsersRepository;
import distribuidora.scrapping.repositories.ClientModuleRepository;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;
import distribuidora.scrapping.util.converters.ClientDtoConverter;
import distribuidora.scrapping.util.converters.LookupValueDtoConverter;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClientHasUsersRepository clientHasUsersRepository;
	
	@Autowired
	private ClientModuleRepository clientModuleRepository;
	
	@Autowired
	private LookupValueDtoConverter lookupValueDtoConverter;
	
	@Autowired
	private ClientDtoConverter clientDtoConverter;

	public UsuarioEntity getCurrentUser() {
		String auth = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return usuarioRepository.findByUsuario(auth);
	}
	
	public ClientDto getCurrentUserDto() {
		return clientDtoConverter.toDto(getCurrentClient());
	}

	public Client getCurrentClient() {
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return clientHasUsersRepository.getClientIdByUsername(username);
	}

	public List<LookupValueDto> getModules() {
		Integer clientId = getCurrentClient().getId();
		List<ClientModule> result = clientModuleRepository.findAll(clientId);
		return result.stream().map(r -> lookupValueDtoConverter.toDto(r.getModule())).toList();
	}

	
	
}
