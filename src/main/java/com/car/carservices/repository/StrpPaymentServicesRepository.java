package com.car.carservices.repository;

import com.car.carservices.entity.PaymentServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrpPaymentServicesRepository extends JpaRepository<PaymentServices, Long> {

    List<PaymentServices> findByMonthAndYearAndStatusIgnoreCase(Integer month, Integer year, String status);
}