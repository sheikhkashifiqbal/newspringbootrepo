// src/main/java/com/yourpkg/config/WebMvcConfig.java
package com.car.carservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Example: expose folder D:/project/uploads to URL /images/**
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/project/uploads/");
    }
}
