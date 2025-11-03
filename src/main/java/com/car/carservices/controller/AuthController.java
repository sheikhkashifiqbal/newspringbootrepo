// com/car/carservices/controller/AuthController.java
package com.car.carservices.controller;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;
//import com.car.carservices.repository.BranchRepository;
import com.car.carservices.security.PRJwtService;
import org.springframework.http.ResponseCookie;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.car.carservices.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
 
public class AuthController {

    private final PRJwtService prJwtService;
    private final AuthService authService; // your existing service
    private final JdbcTemplate jdbcTemplate;
    

    public AuthController(PRJwtService prJwtService, AuthService authService, 
    JdbcTemplate jdbcTemplate) {
        this.prJwtService = prJwtService;
        this.authService = authService;
        this.jdbcTemplate = jdbcTemplate;
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
                .sameSite("none")                // or "None" + secure=true if cross-site
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

                // NEW: fetch branch IDs for this company
                //List<Long> branchIds = branchRepository.findBranchIdsByCompanyId(res.getCompanyId());
                

       List<Map<String, Object>> branchList = jdbcTemplate.query(
            "SELECT branch_id, branch_name FROM branch WHERE company_id = ? ORDER BY branch_id",
            (rs, rowNum) -> {
                Map<String, Object> m = new HashMap<>();
                m.put("branch_id",   rs.getLong("branch_id"));
                m.put("branch_name", rs.getString("branch_name"));
                return m;
            },
            res.getCompanyId()  // <-- your variable that holds the resolved company_id
        );

                // put the list in the response
                loginResponse.put("branch_list", branchList);

                // set branch_id to the first element of branch_list (if present)
                if (!branchList.isEmpty()) {
                    Object idVal = branchList.get(0).get("branch_id");
                    Long firstBranchId = (idVal instanceof Number) ? ((Number) idVal).longValue() : null;
                    if (firstBranchId != null) {
                        loginResponse.put("branch_id", firstBranchId);
                    }
                }
                jwtToken = prJwtService.createAccessToken(
                        res.getEmail(),       // subject (email or userId)
                        60 * 60               // TTL: 60 minutes
                );

                cookie = ResponseCookie.from("access_token", jwtToken)
                        .httpOnly(true)
                        .secure(false)        // set true in production (HTTPS)
                        .sameSite("none")      // or "None" + secure=true if cross-site
                        .path("/")
                        .maxAge(60 * 60)
                        .build();

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
                .sameSite("None")                // or "None" + secure=true if cross-site
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
                .secure(true)                  // true in production
                .sameSite("none")
                .path("/")
                .maxAge(0)                      // expires now
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, delete.toString())
                .build();
    }

}
