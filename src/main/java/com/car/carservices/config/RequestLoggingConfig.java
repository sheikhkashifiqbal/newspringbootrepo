// src/main/java/com/car/carservices/config/RequestLoggingConfig.java
package com.car.carservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter f = new CommonsRequestLoggingFilter();
        f.setIncludeClientInfo(true);
        f.setIncludeQueryString(true);
        f.setIncludeHeaders(false);      // set true if you want headers too
        f.setIncludePayload(true);       // 🔎 include JSON body
        f.setMaxPayloadLength(10_000);   // adjust as needed
        f.setAfterMessagePrefix("Incoming request: ");
        return f;
    }
}
