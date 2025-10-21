// com/car/carservices/controller/AuthController.java
package com.car.carservices.controller;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;
import com.car.carservices.security.PRJwtService;
import org.springframework.http.ResponseCookie;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;

import com.car.carservices.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class AuthController {

    private final PRJwtService prJwtService;
    private final AuthService authService; // your existing service

    public AuthController(PRJwtService prJwtService, AuthService authService) {
        this.prJwtService = prJwtService;
        this.authService = authService;
    }
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
    try {
        LoginResponseDTO res = authService.login(req);
        ResponseCookie cookie;
        String jwtToken;
        Map<String, Object> loginResponse;
        switch (res.getRole()) { // <— changed: decide by ACTUAL authenticated role
            case "branch_manager":
                loginResponse = new HashMap<>();

                loginResponse.put("branch_id",  res.getBranchId());
                loginResponse.put("company_id", res.getCompanyId());
                loginResponse.put("branch_name", res.getBranchName());

                jwtToken = prJwtService.createAccessToken(
                res.getEmail(),                // subject (email or userId)
                60 * 60                         // TTL: 15 minutes
        );

                cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(false)                  // set true in production (HTTPS)
                .sameSite("Lax")                // or "None" + secure=true if cross-site
                .path("/")
                .maxAge(60 * 60)
                .build();

        // Optional: also return token in JSON for Postman/mobile
                loginResponse.put("token", jwtToken);

                return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(loginResponse);

            case "company_manager":

                loginResponse = new HashMap<>();
                loginResponse.put("company_id",  res.getCompanyId());
                loginResponse.put("company_name", res.getCompanyName());
                
                jwtToken = prJwtService.createAccessToken(
                res.getEmail(),                // subject (email or userId)
                60 * 60                         // TTL: 15 minutes
        );

                 cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(false)                  // set true in production (HTTPS)
                .sameSite("Lax")                // or "None" + secure=true if cross-site
                .path("/")
                .maxAge(60 * 60)
                .build();

        // Optional: also return token in JSON for Postman/mobile
                loginResponse.put("token", jwtToken);

                return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(loginResponse);
            case "user":

            loginResponse = new HashMap<>();
                loginResponse.put("id",  res.getId());
                loginResponse.put("full_name", res.getFullName());
                loginResponse.put("email", res.getEmail());

                
                jwtToken = prJwtService.createAccessToken(
                res.getEmail(),                // subject (email or userId)
                60 * 60                         // TTL: 15 minutes
        );

                 cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(false)                  // set true in production (HTTPS)
                .sameSite("Lax")                // or "None" + secure=true if cross-site
                .path("/")
                .maxAge(60 * 60)
                .build();

        // Optional: also return token in JSON for Postman/mobile
                loginResponse.put("token", jwtToken);

                return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(loginResponse);

            default:
                return ResponseEntity.status(500).body("Login error");
        }
    } catch (IllegalStateException notApproved) {
        return ResponseEntity.status(403).body("Account not approved");
    } catch (IllegalArgumentException badCreds) {
        return ResponseEntity.status(401).body("Invalid email or password");
    } catch (Exception ex) {
        return ResponseEntity.status(500).body("Login error");
    }
}

    // Optional: logout clears the cookie
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie delete = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false)                  // true in production
                .sameSite("Lax")
                .path("/")
                .maxAge(0)                      // expires now
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, delete.toString())
                .build();
    }

}
