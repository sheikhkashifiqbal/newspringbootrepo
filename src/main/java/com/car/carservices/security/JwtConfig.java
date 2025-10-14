// com/car/carservices/security/JwtConfig.java
package com.car.carservices.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    /** Base64-encoded secret, e.g. set in application.properties */
    private String secret; 
    /** token validity in seconds */
    private long ttlSeconds = 3600; // 1 hour default

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    public long getTtlSeconds() { return ttlSeconds; }
    public void setTtlSeconds(long ttlSeconds) { this.ttlSeconds = ttlSeconds; }
}
