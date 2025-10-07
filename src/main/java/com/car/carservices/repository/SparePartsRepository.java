package com.car.carservices.repository;

import com.car.carservices.entity.SpareParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SparePartsRepository extends JpaRepository<SpareParts, Long> {
}