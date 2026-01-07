package com.car.carservices.config;

import com.car.carservices.security.PRJwtAuthFilter;
import com.car.carservices.security.PRJwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PRJwtAuthFilter prJwtAuthFilter(PRJwtService prJwtService) {
        return new PRJwtAuthFilter(prJwtService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                               PRJwtAuthFilter prJwtAuthFilter) throws Exception {
    http
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/**").permitAll()
            .anyRequest().permitAll()
        )
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.addFilterBefore(prJwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
}
