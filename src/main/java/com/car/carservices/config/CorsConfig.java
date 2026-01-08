package com.car.carservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ IMPORTANT: When allowCredentials=true, you CANNOT use "*" in allowedOrigins.
        // Use exact origins or allowedOriginPatterns.
        config.setAllowedOriginPatterns(List.of(
                "https://gentle-beach-07ba6f81e.2.azurestaticapps.net",
                "https://*.azurestaticapps.net",
                "http://localhost:3000",
                "http://localhost:3001"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Safer than "*" for some proxies / setups
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // ✅ You are using credentials: 'include' so this MUST be true
        config.setAllowCredentials(true);

        // Optional: if you want frontend to read these headers
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie", "Content-Disposition"));

        // Optional: cache preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
