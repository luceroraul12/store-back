package distribuidora.scrapping.security.service;

import distribuidora.scrapping.security.entity.UsuarioDto;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private ScrappingUserDetails scrappingUserDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    public String generateTokenWithDataUserByUsername(UsuarioDto usuario){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usuario.getUsername(),
                usuario.getPassword()));

        UsuarioEntity entity = usuarioRepository.findByUsuario(usuario.getUsername());
        scrappingUserDetails.loadUserByUsername(usuario.getUsername());

        Map<String, String> mapExtraData = new HashMap<>();
        mapExtraData.put("usuarioId", entity.getId().toString());

        return jwtUtilService.generateToken(mapExtraData);
    }
}
