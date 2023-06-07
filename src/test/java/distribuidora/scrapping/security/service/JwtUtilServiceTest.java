package distribuidora.scrapping.security.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class JwtUtilServiceTest {

    @InjectMocks
    private JwtUtilService jwtUtilService;

    @Test
    void testLeerToken(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJyb2wiOnsiYXV0aG9yaXR5IjoiQ1JFQVRFIn0sInN1YiI6ImhvbWl0b3dlbiIsImlhdCI6MTY4NjAxMjM1MiwiZXhwIjoxNjg2MDQxMTUyfQ.coUKAiWR29sn7SC6Ewt8lqMK4Lqu6uF6jIwpUSA7-EY";

        String username = jwtUtilService.extractUsername(jwt);
        System.out.println(username);
    }

    @Test
    void testValidateToken(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJyb2wiOnsiYXV0aG9yaXR5IjoiQ1JFQVRFIn0sInN1YiI6ImhvbWl0b3dlbiIsImlhdCI6MTY4NjAxMjM1MiwiZXhwIjoxNjg2MDQxMTUyfQ.coUKAiWR29sn7SC6Ewt8lqMK4Lqu6uF6jIwpUSA7-EY";
        UserDetails user = User.builder()
                .username("homitowen")
                .password("1324")
                .roles("ADMIN")
                .authorities("CREATE","UPDATE","DELETE")
                .build();
        Assertions.assertFalse(jwtUtilService.validateToken(jwt, user));
    }
}