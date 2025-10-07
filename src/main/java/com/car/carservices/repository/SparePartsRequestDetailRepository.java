// src/main/java/com/car/carservices/repository/SparePartsRequestDetailRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.SparePartsRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SparePartsRequestDetailRepository extends JpaRepository<SparePartsRequestDetail, Long> {
    List<SparePartsRequestDetail> findBySparepartsrequestId(Long sparepartsrequestId);
    long deleteBySparepartsrequestId(Long sparepartsrequestId);
}
