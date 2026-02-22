package com.car.carservices.repository;

import com.car.carservices.entity.StrpStripeCompanySubs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrpStripeCompanySubsRepository extends JpaRepository<StrpStripeCompanySubs, Long> {

    List<StrpStripeCompanySubs> findByCompany_CompanyId(Long companyId);
}
