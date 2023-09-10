package distribuidora.scrapping.security;

import distribuidora.scrapping.security.entity.UsuarioDto;
import distribuidora.scrapping.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody UsuarioDto usuario){
        Map<String, String> map = new HashMap<>();
        map.put("jwt", loginService.generateTokenWithDataUserByUsername(usuario));
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
