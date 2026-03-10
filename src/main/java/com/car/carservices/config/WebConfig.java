package com.car.carservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Read from environment variable UPLOAD_DIR, default to /home/site/wwwroot/images
    private static final String imagesDir =
            System.getenv("UPLOAD_DIR") != null ? System.getenv("UPLOAD_DIR") : "/home/site/wwwroot/images";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Ensure the folder exists
        try {
            Files.createDirectories(Paths.get(imagesDir));
        } catch (Exception e) {
            e.printStackTrace();
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + ensureTrailingSlash(imagesDir))
                .setCachePeriod(3600);
    }

    private String ensureTrailingSlash(String path) {
        if (path == null || path.isBlank()) return "";
        return path.endsWith("/") ? path : path + "/";
    }
}