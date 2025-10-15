// com/car/carservices/controller/AuthController.java
package com.car.carservices.controller;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;
import com.car.carservices.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class AuthController {

    private final AuthService service;
    public AuthController(AuthService service){ this.service = service; }

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
    try {
        LoginResponseDTO res = service.login(req);

        switch (res.getRole()) { // <— changed: decide by ACTUAL authenticated role
            case "branch_manager":
                return ResponseEntity.ok(new java.util.LinkedHashMap<>() {{
                    put("branch_id",  res.getBranchId());
                    put("company_id", res.getCompanyId());
                    put("branch_name", res.getBranchName());
                    put("token",      res.getToken());
                }});
            case "company_manager":
                return ResponseEntity.ok(new java.util.LinkedHashMap<>() {{
                    put("company_id",   res.getCompanyId());
                    put("company_name", res.getCompanyName());
                    put("token",        res.getToken());
                }});
            case "user":
                return ResponseEntity.ok(new java.util.LinkedHashMap<>() {{
                    put("id",        res.getId());
                    put("full_name", res.getFullName());
                    put("email",     res.getEmail());
                    put("token",     res.getToken());
                }});
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

}
