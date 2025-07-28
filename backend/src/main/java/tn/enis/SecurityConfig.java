package tn.enis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les tests ou les API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()// autoriser l'accès à /api/learn sans authentification
                        .anyRequest().authenticated()                 // le reste nécessite une authentification
                );

        return http.build();
    }
}
