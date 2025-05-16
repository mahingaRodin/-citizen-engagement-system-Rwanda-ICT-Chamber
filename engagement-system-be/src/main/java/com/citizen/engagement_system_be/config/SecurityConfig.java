package com.citizen.engagement_system_be.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //Swagger UI v3 (OpenAPI)
                        .requestMatchers("v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html/**").permitAll()

                        //Actuator endpoints
                        .requestMatchers("/actuator/**").permitAll()
                        //public endpoints
                        .requestMatchers("/api/public/**").permitAll()
                        //all other requests need authentication
                        .anyRequest().authenticated()
                );
                return http.build();
    }

}
