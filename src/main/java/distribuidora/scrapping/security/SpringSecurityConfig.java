package distribuidora.scrapping.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private ScrappingUserDetails userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityContext().requireExplicitSave(false)
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/inventory-system/")
                    .hasAuthority("ALL")
                .antMatchers("/scrapping")
                    .hasAuthority("ALL")
                .antMatchers("/actualizar")
                    .hasAuthority("ALL")
                .anyRequest().permitAll()
                .and().httpBasic();
        return http.build();
    }
}
