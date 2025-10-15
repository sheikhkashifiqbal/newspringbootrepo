// com/car/carservices/service/PRVerifyContactService.java
package com.car.carservices.service;

import com.car.carservices.dto.PRVerifyContactRequest;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.repository.CompanyRepository;
import com.car.carservices.repository.UserRegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class PRVerifyContactService {

    private final UserRegistrationRepository userRepo;
    private final BranchRepository branchRepo;
    private final CompanyRepository companyRepo;

    public PRVerifyContactService(UserRegistrationRepository userRepo,
                                  BranchRepository branchRepo,
                                  CompanyRepository companyRepo) {
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
        this.companyRepo = companyRepo;
    }

    public boolean verify(PRVerifyContactRequest req) {
        String type = safe(req.getType());
        String email = safe(req.getEmail());
        String mobile = safe(req.getMobile());

        switch (type) {
            case "user":
                // 1) user_registration.email
                if (!email.isEmpty() && userRepo.existsByEmailIgnoreCase(email)) return true;
                // 2) user_registration.mobile
                if (!mobile.isEmpty() && userRepo.existsByMobileIgnoreCase(mobile)) return true;
                return false;

            case "manager":
                // 1) branch.login_email
                if (!email.isEmpty() && branchRepo.existsByLoginEmailIgnoreCase(email)) return true;
                // 2) company.manager_email
                if (!email.isEmpty() && companyRepo.existsByManagerEmailIgnoreCase(email)) return true;
                // 3) company.manager_mobile
                if (!mobile.isEmpty() && companyRepo.existsByManagerMobileIgnoreCase(mobile)) return true;
                return false;

            default:
                return false;
        }
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
