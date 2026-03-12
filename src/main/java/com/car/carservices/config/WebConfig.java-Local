package com.car.carservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.images-dir}")
    private String imagesDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve: http://localhost:8081/images/<filename>
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + ensureTrailingSlash(imagesDir));
    }

    private String ensureTrailingSlash(String path) {
        if (path == null || path.isBlank()) return "";
        return path.endsWith("/") ? path : path + "/";
    }
}