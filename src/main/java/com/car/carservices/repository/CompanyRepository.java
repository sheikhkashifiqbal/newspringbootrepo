package com.car.carservices.repository;

import com.car.carservices.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Create checks (any row)
    boolean existsByManagerEmailIgnoreCase(String managerEmail);
    boolean existsByCompanyNameIgnoreCase(String companyName);
    boolean existsByBrandNameIgnoreCase(String brandName);

    // Update checks (exclude current row by ID)
    boolean existsByManagerEmailIgnoreCaseAndCompanyIdNot(String managerEmail, Long companyId);
    boolean existsByCompanyNameIgnoreCaseAndCompanyIdNot(String companyName, Long companyId);
    boolean existsByBrandNameIgnoreCaseAndCompanyIdNot(String brandName, Long companyId);

      
    boolean existsByManagerMobileIgnoreCase(String managerMobile);

    Optional<Company> findFirstByManagerEmailIgnoreCase(String managerEmail);
    Optional<Company> findFirstByManagerMobileIgnoreCase(String managerMobile);

}
