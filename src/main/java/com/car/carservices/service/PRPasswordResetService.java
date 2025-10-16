// src/main/java/com/car/carservices/service/PRPasswordResetService.java
package com.car.carservices.service;

import com.car.carservices.dto.PRResetPasswordRequest;
import com.car.carservices.dto.PRResetPasswordResponse;
import com.car.carservices.entity.Branch;
import com.car.carservices.entity.Company;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.repository.CompanyRepository;
import com.car.carservices.repository.UserRegistrationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PRPasswordResetService {

    private final UserRegistrationRepository userRepo;
    private final BranchRepository branchRepo;
    private final CompanyRepository companyRepo;
    private final PasswordEncoder passwordEncoder;

    public PRPasswordResetService(UserRegistrationRepository userRepo,
                                  BranchRepository branchRepo,
                                  CompanyRepository companyRepo,
                                  PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
        this.companyRepo = companyRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PRResetPasswordResponse reset(PRResetPasswordRequest req) {
        String type   = safe(req.getType());
        String email  = safe(req.getEmail());
        String mobile = safe(req.getMobile());
        String raw    = safe(req.getPassword());

        if (raw.isEmpty()) {
            return new PRResetPasswordResponse(false, "Password is not changed", null, null);
        }

        switch (type) {
            case "user":
                // 1) user_registration.email
                if (!email.isEmpty()) {
                    var hit = userRepo.findFirstByEmailIgnoreCase(email);
                    if (hit.isPresent()) {
                        UserRegistration u = hit.get();
                        u.setPassword(passwordEncoder.encode(raw));
                        userRepo.save(u);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "email", email);
                    }
                }
                // 2) user_registration.mobile
                if (!mobile.isEmpty()) {
                    var hit = userRepo.findFirstByMobileIgnoreCase(mobile);
                    if (hit.isPresent()) {
                        UserRegistration u = hit.get();
                        u.setPassword(passwordEncoder.encode(raw));
                        userRepo.save(u);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "mobile", mobile);
                    }
                }
                return new PRResetPasswordResponse(false, "Password is not changed", null, null);

            case "manager":
                // 1) branch.login_email
                if (!email.isEmpty()) {
                    var bOpt = branchRepo.findFirstByLoginEmailIgnoreCase(email);
                    if (bOpt.isPresent()) {
                        Branch b = bOpt.get();
                        b.setPassword(passwordEncoder.encode(raw));
                        branchRepo.save(b);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "email", email);
                    }
                }
                // 2) company.manager_email
                if (!email.isEmpty()) {
                    var cOpt = companyRepo.findFirstByManagerEmailIgnoreCase(email);
                    if (cOpt.isPresent()) {
                        Company c = cOpt.get();
                        c.setPassword(passwordEncoder.encode(raw));
                        companyRepo.save(c);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "email", email);
                    }
                }
                // 3) branch.mobile  (⚠ remove this block if branch has no 'mobile' column)
                if (!mobile.isEmpty()) {
                    var bMob = branchRepo.findFirstByMobileIgnoreCase(mobile);
                    if (bMob.isPresent()) {
                        Branch b = bMob.get();
                        b.setPassword(passwordEncoder.encode(raw));
                        branchRepo.save(b);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "mobile", mobile);
                    }
                }
                // 4) company.manager_mobile
                if (!mobile.isEmpty()) {
                    var cMob = companyRepo.findFirstByManagerMobileIgnoreCase(mobile);
                    if (cMob.isPresent()) {
                        Company c = cMob.get();
                        c.setPassword(passwordEncoder.encode(raw));
                        companyRepo.save(c);
                        return new PRResetPasswordResponse(true, "Password is modified successfully", "mobile", mobile);
                    }
                }
                return new PRResetPasswordResponse(false, "Password is not changed", null, null);

            default:
                return new PRResetPasswordResponse(false, "Password is not changed", null, null);
        }
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
