package com.car.carservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    // Azure-safe directory
    private static final String UPLOAD_BASE_DIR =
            Optional.ofNullable(System.getenv("UPLOAD_DIR"))
                    .orElse("/home/site/wwwroot/images");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadPath = Paths.get(UPLOAD_BASE_DIR).toAbsolutePath().normalize();

        String location = "file:" + uploadPath.toString() + "/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations(location)
                .setCachePeriod(3600);
    }
}