// src/main/java/com/car/carservices/security/PRJwtAuthFilter.java
package com.car.carservices.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
//import java.util.List;

public class PRJwtAuthFilter extends OncePerRequestFilter {

    private final PRJwtService jwt;

    public PRJwtAuthFilter(PRJwtService jwt) { this.jwt = jwt; }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String token = headerToken(req);
        if (token == null) token = cookieToken(req, "access_token");

        if (token != null && jwt.isValid(token)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            String subject = jwt.getSubject(token);
            var auth = new UsernamePasswordAuthenticationToken(subject, null, java.util.List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }


        chain.doFilter(req, res);
    }

    private String headerToken(HttpServletRequest req) {
        String h = req.getHeader("Authorization");
        return (h != null && h.startsWith("Bearer ")) ? h.substring(7) : null;
    }

    private String cookieToken(HttpServletRequest req, String name) {
        Cookie[] cs = req.getCookies();
        if (cs == null) return null;
        return Arrays.stream(cs)
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
