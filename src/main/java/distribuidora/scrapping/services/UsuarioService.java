package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	UsuarioEntity getCurrentUser() {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepository.findByUsuario(auth);
	}
}
