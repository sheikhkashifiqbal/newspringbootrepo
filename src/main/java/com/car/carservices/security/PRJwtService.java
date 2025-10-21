// src/main/java/com/car/carservices/security/PRJwtService.java
package com.car.carservices.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PRJwtService {

    private static final String DEFAULT_SECRET = "change-me-32-bytes-minimum-secret-key!";
    private final Key key = Keys.hmacShaKeyFor(
            System.getenv().getOrDefault("JWT_SECRET", DEFAULT_SECRET).getBytes(StandardCharsets.UTF_8)
    );

    /** Old method (still works). You can keep it if other code uses it. */
    public String createAccessToken(String subject, List<String> roles, long ttlSeconds) {
        long nowMs = System.currentTimeMillis();
        Date now = new Date(nowMs);
        Date exp = new Date(nowMs + ttlSeconds * 1000L);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                // roles are optional; omit if null
                .addClaims(roles == null ? Map.of() : Map.of("roles", roles))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** NEW: no-roles overload */
    public String createAccessToken(String subject, long ttlSeconds) {
        long nowMs = System.currentTimeMillis();
        Date now = new Date(nowMs);
        Date exp = new Date(nowMs + ttlSeconds * 1000L);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
