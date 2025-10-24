// com/car/carservices/service/AuthService.java
package com.car.carservices.service;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;
import com.car.carservices.repository.UserRegistrationRepository;
import com.car.carservices.repository.CompanyRepository;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final UserRegistrationRepository userRepo;
    private final CompanyRepository companyRepo;
    private final BranchRepository branchRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    public AuthService(UserRegistrationRepository userRepo,
                       CompanyRepository companyRepo,
                       BranchRepository branchRepo,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwt) {
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.branchRepo = branchRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

   // com/car/carservices/service/AuthService.java
public LoginResponseDTO login(LoginRequestDTO req) {
    String type = req.getType() == null ? "" : req.getType().trim().toLowerCase();
    switch (type) {
        case "user":
            return loginUser(req);
        case "company_manager":
            return loginCompanyManager(req);
        case "branch_manager":
            // NEW: try branch first; if it fails for ANY reason, try company_manager
            try {
                return loginBranchManager(req);
            } catch (RuntimeException ignored) {
                // fall back to company manager
                return loginCompanyManager(req);
            }
        default:
            throw new IllegalArgumentException("Unsupported type: " + req.getType());
    }
}

private LoginResponseDTO loginUser(LoginRequestDTO req) {
    var user = userRepo.findFirstByEmailIgnoreCase(req.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    String token = jwt.createToken(
            "user:" + user.getId(),
            "user",
            Map.of("email", user.getEmail(), "id", user.getId())
    );

    return LoginResponseDTO.builder()
            .role("user")
            .token(token)
            .id(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .build();
}

private LoginResponseDTO loginCompanyManager(LoginRequestDTO req) {
    var c = companyRepo.findFirstByManagerEmailIgnoreCase(req.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));


    if (c.getPassword() == null || !passwordEncoder.matches(req.getPassword(), c.getPassword())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    String token = jwt.createToken(
            "company_manager:" + c.getCompanyId(),
            "company_manager",
            Map.of("company_id", c.getCompanyId(), "manager_email", c.getManagerEmail())
    );

    return LoginResponseDTO.builder()
            .role("company_manager")
            .token(token)
            .companyId(c.getCompanyId())
            .companyName(c.getCompanyName())
            .build();
}

private LoginResponseDTO loginBranchManager(LoginRequestDTO req) {
    var b = branchRepo.findFirstByLoginEmailIgnoreCase(req.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (b.getStatus() == null || !b.getStatus().equalsIgnoreCase("approved")) {
        throw new IllegalStateException("Account not approved");
    }
    if (b.getPassword() == null || !passwordEncoder.matches(req.getPassword(), b.getPassword())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    String token = jwt.createToken(
            "branch_manager:" + b.getBranchId(),
            "branch_manager",
            Map.of(
                "branch_id", b.getBranchId(),
                "company_id", b.getCompany() != null ? b.getCompany().getCompanyId() : null,
                "login_email", b.getLoginEmail()
            )
    );

    return LoginResponseDTO.builder()
            .role("branch_manager")
            .token(token)
            .branchId(b.getBranchId())
            .branchName(b.getBranchName())
            .companyId(b.getCompany() != null ? b.getCompany().getCompanyId() : null)
            .build();
}

}
