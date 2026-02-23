package com.car.carservices.repository;

import com.car.carservices.entity.PaymentServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentServicesRepository extends JpaRepository<PaymentServices, Long> {

    Optional<PaymentServices> findByBranchIdAndMonthAndYear(Long branchId, Integer month, Integer year);
}