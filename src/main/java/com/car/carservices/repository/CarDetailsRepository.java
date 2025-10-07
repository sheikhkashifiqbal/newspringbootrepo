package com.car.carservices.repository;

import com.car.carservices.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    List<CarDetails> findByUserId(Long userId);
    Optional<CarDetails> findByVinNumber(String vinNumber);
}
