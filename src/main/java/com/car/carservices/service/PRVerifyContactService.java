// com/car/carservices/service/PRVerifyContactService.java
package com.car.carservices.service;

import com.car.carservices.dto.PRVerifyContactRequest;
import com.car.carservices.dto.PRVerifyContactResponse;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.repository.CompanyRepository;
import com.car.carservices.repository.UserRegistrationRepository;
import com.car.carservices.util.PRCodeUtil;
import org.springframework.stereotype.Service;

@Service
public class PRVerifyContactService {

    private final UserRegistrationRepository userRepo;
    private final BranchRepository branchRepo;
    private final CompanyRepository companyRepo;
    private final PRNotificationService notification;

    public PRVerifyContactService(UserRegistrationRepository userRepo,
                                  BranchRepository branchRepo,
                                  CompanyRepository companyRepo,
                                  PRNotificationService notification) {
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
        this.companyRepo = companyRepo;
        this.notification = notification;
    }

    public PRVerifyContactResponse verify(PRVerifyContactRequest req) {
        String type = safe(req.getType());
        String email = safe(req.getEmail());
        String mobile = safe(req.getMobile());

        // Order is exactly as you specified; we return on first match
        if ("user".equals(type)) {
            if (!email.isEmpty() && userRepo.existsByEmailIgnoreCase(email)) {
                return respondAndNotify("email", email);
            }
            if (!mobile.isEmpty() && userRepo.existsByMobileIgnoreCase(mobile)) {
                return respondAndNotify("mobile", mobile);
            }
            return noMatch();
        }

        if ("manager".equals(type)) {
            if (!email.isEmpty() && branchRepo.existsByLoginEmailIgnoreCase(email)) {
                return respondAndNotify("email", email);
            }
            if (!email.isEmpty() && companyRepo.existsByManagerEmailIgnoreCase(email)) {
                return respondAndNotify("email", email);
            }
            if (!mobile.isEmpty() && companyRepo.existsByManagerMobileIgnoreCase(mobile)) {
                return respondAndNotify("mobile", mobile);
            }
            return noMatch();
        }

        // Unsupported type -> no match
        return noMatch();
    }

    private PRVerifyContactResponse respondAndNotify(String matchedType, String matchedValue) {
        String code = PRCodeUtil.fourDigitCode();
        try {
            if ("email".equals(matchedType)) {
                notification.sendEmailCode(matchedValue, code, "[LOCAL]");
            } else if ("mobile".equals(matchedType)) {
                notification.sendSmsCode(matchedValue, code);
            }
        } catch (Exception ignored) {
            // We still return the code in JSON even if sending fails in local dev
        }
        return new PRVerifyContactResponse(true, matchedType, matchedValue, code);
    }

    private PRVerifyContactResponse noMatch() {
        return new PRVerifyContactResponse(false, null, null, null);
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
