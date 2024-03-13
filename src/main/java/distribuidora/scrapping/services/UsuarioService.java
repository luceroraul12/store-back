package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.repositories.ClientHasUsersRepository;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClientHasUsersRepository clientHasUsersRepository;

	public UsuarioEntity getCurrentUser() {
		String auth = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return usuarioRepository.findByUsuario(auth);
	}

	public Client getCurrentClient() {
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return clientHasUsersRepository.getClientIdByUsername(username);
	}
}
