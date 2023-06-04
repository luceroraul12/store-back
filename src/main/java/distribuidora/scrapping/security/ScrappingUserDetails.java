package distribuidora.scrapping.security;

import distribuidora.scrapping.security.entity.RolEntity;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;
import distribuidora.scrapping.security.repository.UsuarioTieneRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrappingUserDetails implements UserDetailsService {


    @Autowired
    private UsuarioTieneRolRepository usuarioTieneRolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsuario(username);
        if (usuario == null)
            throw new UsernameNotFoundException("El usuario no existe");

        List<RolEntity> roles = new ArrayList<>();
        if (usuario != null){
            roles = usuarioTieneRolRepository.getRolesDelUsuario(username);
        }

        return User
                .withUsername(username)
                .password(usuario.getPasswordHash())
                .authorities(roles)
                .build();
    }
}
