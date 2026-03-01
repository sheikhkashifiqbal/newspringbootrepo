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
//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")

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

            if (res == null) {
                return ResponseEntity.status(500).body("Login error: empty response from authService");
            }

            String role = res.getRole() == null ? "" : res.getRole().trim().toLowerCase();

            // ✅ request type from JSON (as per your requirement)
            String requestedType = req == null || req.getType() == null ? "" : req.getType().trim().toLowerCase();

            String jwtToken = prJwtService.createAccessToken(res.getEmail(), 60 * 60);

            ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                    .httpOnly(true)
                    .secure(true)      // ✅ required for SameSite=None
                    .sameSite("None")  // ✅ required for cross-site cookie
                    .path("/")
                    .maxAge(60 * 60)
                    .build();

            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("token", jwtToken);

            switch (role) {
                case "branch_manager": {
                    loginResponse.put("branch_id", res.getBranchId());
                    loginResponse.put("company_id", res.getCompanyId());
                    loginResponse.put("branch_name", res.getBranchName());
                    loginResponse.put("email", res.getEmail());

                    // ✅ NEW: user_type
                    String userType = resolveUserType(requestedType, role, res.getBranchId());
                    loginResponse.put("user_type", userType);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .body(loginResponse);
                }

                case "company_manager": {
                    loginResponse.put("company_id", res.getCompanyId());
                    loginResponse.put("company_name", res.getCompanyName());
                    loginResponse.put("email", res.getEmail());

                    List<Map<String, Object>> branchList = jdbcTemplate.query(
                            "SELECT branch_id, branch_name FROM branch WHERE company_id = ? ORDER BY branch_id",
                            (rs, rowNum) -> {
                                Map<String, Object> m = new HashMap<>();
                                m.put("branch_id", rs.getLong("branch_id"));
                                m.put("branch_name", rs.getString("branch_name"));
                                return m;
                            },
                            res.getCompanyId()
                    );

                    loginResponse.put("branch_list", branchList);

                    Long selectedBranchId = null;
                    if (!branchList.isEmpty()) {
                        Object idVal = branchList.get(0).get("branch_id");
                        if (idVal instanceof Number n) {
                            selectedBranchId = n.longValue();
                            loginResponse.put("branch_id", selectedBranchId);
                        }
                    }

                    // ✅ NEW: user_type (based on first branch_id just like your existing behavior)
                    String userType = resolveUserType(requestedType, role, selectedBranchId);
                    loginResponse.put("user_type", userType);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .body(loginResponse);
                }

                case "user": {
                    loginResponse.put("id", res.getId());
                    loginResponse.put("full_name", res.getFullName());
                    loginResponse.put("email", res.getEmail());

                    // ✅ NEW: user_type for normal user
                    loginResponse.put("user_type", "user");

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .body(loginResponse);
                }

                default:
                    // ✅ don’t hide the real issue
                    return ResponseEntity.status(500).body("Login error: unknown role=" + res.getRole());
            }

        } catch (IllegalStateException notApproved) {
            return ResponseEntity.status(403).body("Account not approved");
        } catch (IllegalArgumentException badCreds) {
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (Exception ex) {
            // ✅ At least log server-side
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Login error: " + ex.getMessage());
        }
    }

    /**
     * ✅ NEW helper:
     * If request type is company_manager/branch_manager -> determine store type by branch_id:
     * - branch_brand_service exists -> services_store
     * - branch_brand_spare_part exists -> spareparts_store
     * If request type is user -> user
     */
    private String resolveUserType(String requestedType, String role, Long branchId) {

        String t = (requestedType == null ? "" : requestedType.trim().toLowerCase());
        String r = (role == null ? "" : role.trim().toLowerCase());

        // If user explicitly requested "user", always return "user"
        if ("user".equals(t)) {
            return "user";
        }

        // Apply store logic only when request type is manager (as per requirement)
        if ("company_manager".equals(t) || "branch_manager".equals(t)) {

            if (branchId == null) {
                // fallback if branch id not available
                return t;
            }

            Boolean hasServices = jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM branch_brand_service WHERE branch_id = ?)",
                    Boolean.class,
                    branchId
            );
            if (Boolean.TRUE.equals(hasServices)) {
                return "services_store";
            }

            Boolean hasSpareparts = jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM branch_brand_spare_part WHERE branch_id = ?)",
                    Boolean.class,
                    branchId
            );
            if (Boolean.TRUE.equals(hasSpareparts)) {
                return "spareparts_store";
            }

            // if neither table has records for that branch
            return t;
        }

        // If request type is missing/other, fallback to authenticated role
        if ("user".equals(r)) return "user";
        if ("company_manager".equals(r) || "branch_manager".equals(r)) {
            // if role is manager but request type wasn't manager, we keep role
            return r;
        }

        return "user";
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