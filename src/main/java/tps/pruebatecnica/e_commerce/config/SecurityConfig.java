package tps.pruebatecnica.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permitir todas las solicitudes
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si no usas formularios
            .formLogin(login -> login.disable()) // Deshabilitar el login por formulario
            .httpBasic(basic -> basic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}
