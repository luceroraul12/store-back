package distribuidora.scrapping.security;

import distribuidora.scrapping.security.entity.RolEntity;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;
import distribuidora.scrapping.security.repository.UsuarioTieneRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ScrappingAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UsuarioTieneRolRepository usuarioTieneRolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();


        UsuarioEntity usuario = usuarioRepository.findByUsuario(username);
        if (usuario == null)
            throw new UsernameNotFoundException("El usuario no existe");

        if (!passwordEncoder.matches(pwd, usuario.getPasswordHash()))
            throw new UsernameNotFoundException("Credencial incorrecta");

        List<RolEntity> roles = new ArrayList<>();
        if (usuario != null){
            roles = usuarioTieneRolRepository.getRolesDelUsuario(username);
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, roles);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<RolEntity> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RolEntity authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo()));
        }
        return grantedAuthorities;
    }


    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
