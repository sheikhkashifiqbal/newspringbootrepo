package com.car.carservices.repository;

import com.car.carservices.entity.LmpsService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LmpsServiceRepository extends JpaRepository<LmpsService, Long> {

    List<LmpsService> findByService_ServiceId(Long serviceId);

    List<LmpsService> findByCity_Id(Long cityId);

    List<LmpsService> findByService_ServiceIdAndCity_Id(Long serviceId, Long cityId);

    // ✅ NEW: uniqueness checks
    boolean existsByService_ServiceIdAndCity_Id(Long serviceId, Long cityId);

    boolean existsByService_ServiceIdAndCity_IdAndLmpsServiceIdNot(Long serviceId, Long cityId, Long lmpsServiceId);
}
