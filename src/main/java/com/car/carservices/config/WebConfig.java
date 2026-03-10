package com.car.carservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Read UPLOAD_DIR environment variable; fallback to default if missing
    private static final String imagesDir =
            System.getenv("UPLOAD_DIR") != null ? System.getenv("UPLOAD_DIR") : "/home/site/wwwroot/images";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Ensure the folder exists
        try {
            Files.createDirectories(Paths.get(imagesDir));
            System.out.println("Serving images from directory: " + imagesDir);
        } catch (Exception e) {
            System.err.println("Failed to create images directory: " + imagesDir);
            e.printStackTrace();
        }

        // Map /images/** URLs to the imagesDir folder
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + ensureTrailingSlash(imagesDir))
                .setCachePeriod(3600); // cache for 1 hour
    }

    // Ensure the directory path ends with a slash
    private String ensureTrailingSlash(String path) {
        if (path == null || path.isBlank()) return "";
        return path.endsWith("/") ? path : path + "/";
    }
}