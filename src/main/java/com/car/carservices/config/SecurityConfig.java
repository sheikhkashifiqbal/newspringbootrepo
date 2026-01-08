package com.car.carservices.config;

import com.car.carservices.security.PRJwtAuthFilter;
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

    private final PRJwtAuthFilter prJwtAuthFilter;

    public SecurityConfig(PRJwtAuthFilter prJwtAuthFilter) {
        this.prJwtAuthFilter = prJwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ✅ This makes Spring Security apply CorsConfigurationSource
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // ✅ Always permit preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // (adjust these as you need)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/**").permitAll()

                .anyRequest().permitAll()
            );

        http.addFilterBefore(prJwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
