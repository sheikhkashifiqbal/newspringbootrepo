// com/car/carservices/security/SecurityConfig.java
package com.car.carservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final PRJwtAuthFilter prJwtAuthFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(PRJwtAuthFilter prJwtAuthFilter,
                          CorsConfigurationSource corsConfigurationSource) {
        this.prJwtAuthFilter = prJwtAuthFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ✅ IMPORTANT: This makes Spring Security use YOUR CorsConfig.java bean
            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            // If you are stateless JWT, usually disable CSRF
            .csrf(csrf -> csrf.disable())

            // ✅ Stateless API (no server session)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                // ✅ VERY IMPORTANT: allow preflight requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ Login/Register endpoints must be public
                .requestMatchers("/api/auth/**").permitAll()

                // If you have swagger:
                // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // For now, allow everything else (like your current setup)
                .anyRequest().permitAll()
            )

            // Optional: if you don’t use form login / basic
            .httpBasic(Customizer.withDefaults());

        // ✅ JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(prJwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Only needed if you are using AuthenticationManager somewhere.
     * If you don't use it, you can remove this bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
