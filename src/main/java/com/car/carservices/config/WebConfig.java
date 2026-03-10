package com.car.carservices.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    // Use environment variable UPLOAD_DIR if set, otherwise default to Azure path
    private static final String imagesDir =
            System.getenv("UPLOAD_DIR") != null
                    ? System.getenv("UPLOAD_DIR")
                    : "/home/site/wwwroot/images";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        try {
            Path uploadPath = Paths.get(imagesDir).toAbsolutePath().normalize();

            // Ensure the folder exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Created images directory at: {}", uploadPath);
            } else {
                logger.info("Serving images from existing directory: {}", uploadPath);
            }

            // Map /images/** URLs to this folder
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:" + ensureTrailingSlash(uploadPath.toString()))
                    .setCachePeriod(3600); // 1 hour cache

        } catch (Exception e) {
            logger.error("Failed to initialize images directory at {}", imagesDir, e);
        }
    }

    private String ensureTrailingSlash(String path) {
        if (path == null || path.isBlank()) return "";
        return path.endsWith("/") ? path : path + "/";
    }
}