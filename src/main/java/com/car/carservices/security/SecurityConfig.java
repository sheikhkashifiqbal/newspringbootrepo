// src/main/java/com/yourpkg/config/SecurityConfig.java
package com.car.carservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final PRJwtService jwt;

    public SecurityConfig(PRJwtService jwt) { this.jwt = jwt; }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))   // ← enable CORS support
            .csrf(csrf -> csrf.disable())      // likely disabled for stateless APIs
            .authorizeHttpRequests(auth -> auth
                // PUBLIC endpoints (no token required)
                .requestMatchers(
                        "/api/auth/register",
                        "/health",
                        "/public/**",
                        "/api/auth/**"
                ).permitAll()
                 .requestMatchers(
                                "/images/**",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/favicon.ico",
                                "/cover-1.jpg"
                            ).permitAll()

                // PROTECTED endpoints (token required)
                .requestMatchers("/api/**").permitAll() 
              //.requestMatchers("/api/reservations/by-user/**").permitAll() 
              
                // .requestMatchers("/api/reservations/status/cancelled/**", 
                                 //   "/api/reservations/**",
                                   // "/api/reservations/by-user/**"
                                    
                // ).authenticated()

                // everything else stays public for now (adjust to your needs)
                .anyRequest().permitAll()
        );
        http.addFilterBefore(new PRJwtAuthFilter(jwt),
        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

@Bean
org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
    var c = new org.springframework.web.cors.CorsConfiguration();
    c.setAllowedOrigins(java.util.List.of("http://localhost:3000")); // your frontend origin(s)
    c.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","OPTIONS"));
    c.setAllowedHeaders(java.util.List.of("Content-Type","Authorization"));
    c.setAllowCredentials(true); // IMPORTANT for cookies
    c.setMaxAge(3600L);
    var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", c);
    return source;
}
}
