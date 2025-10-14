// com/car/carservices/security/JwtTokenProvider.java
package com.car.carservices.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final JwtConfig cfg;

    public JwtTokenProvider(JwtConfig cfg) {
        this.cfg = cfg;
    }

    private Key key() {
        // secret should be Base64; store in application.properties as app.jwt.secret=BASE64...
        return Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(cfg.getSecret()));
    }

    public String createToken(String subject, String role, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(cfg.getTtlSeconds());
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key())
                .compact();
    }
}
