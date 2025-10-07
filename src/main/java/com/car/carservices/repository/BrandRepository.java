// com/car/carservices/repository/BrandRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandNameIgnoreCase(String name);
}
