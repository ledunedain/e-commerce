package tps.pruebatecnica.e_commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                return java.util.Optional.of(((UserDetails) principal).getUsername());
            }
            return java.util.Optional.of("SYSTEM"); // En caso de que no haya usuario autenticado
        };
    }
}
