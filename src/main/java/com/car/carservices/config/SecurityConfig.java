// src/main/java/com/yourpkg/config/SecurityConfig.java
package com.car.carservices.config;

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())   // ← enable CORS support
            .csrf(csrf -> csrf.disable())      // likely disabled for stateless APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight
                .requestMatchers("/api/**").permitAll() 
                .requestMatchers(
                                "/images/**",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/favicon.ico",
                                "/cover-1.jpg"
                            ).permitAll()
                            .requestMatchers("/api/**").permitAll()                // adjust as needed
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        // In dev, explicitly allow your frontend origin:
        cors.setAllowedOrigins(List.of("http://localhost:3000"));
        // If you need cookies/Authorization header across origins:
        cors.setAllowCredentials(true);

        cors.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cors.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With","Accept","Origin"));
        cors.setExposedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
