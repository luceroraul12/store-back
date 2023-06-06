package distribuidora.scrapping.security;

import distribuidora.scrapping.security.entity.UsuarioDto;
import distribuidora.scrapping.security.entity.UsuarioEntity;
import distribuidora.scrapping.security.service.JwtUtilService;
import distribuidora.scrapping.security.service.ScrappingUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController

public class LoginController {

    @Autowired
    private ScrappingUserDetails scrappingUserDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody UsuarioDto usuario){
        UserDetails user = scrappingUserDetails.loadUserByUsername(usuario.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usuario.getUsername(),
                usuario.getPassword()));

        UserDetails userDetails = scrappingUserDetails.loadUserByUsername(
                usuario.getUsername());

        String jwt = jwtUtilService.generateToken(userDetails);


        Map<String, String> map = new HashMap<>();
        map.put("jwt", jwt);
        return map;
    }

    @GetMapping("/myData")
    public Map<String, Object> myData(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>();
        map.put("myUsername", auth.getName());
        map.put("myAuthorities", auth.getAuthorities().toArray());
        return map;
    }

}
