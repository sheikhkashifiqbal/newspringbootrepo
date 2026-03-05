// src/main/java/com/car/carservices/repository/SparePartsRequestDetailRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.SparePartsRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SparePartsRequestDetailRepository extends JpaRepository<SparePartsRequestDetail, Long> {
    List<SparePartsRequestDetail> findBySparepartsrequestId(Long sparepartsrequestId);
    long deleteBySparepartsrequestId(Long sparepartsrequestId);

    // ✅ NEW: bulk update payment_status by sparepartsrequest_id
    @Modifying
    @Query("""
        update SparePartsRequestDetail d
           set d.paymentStatus = :paymentStatus
         where d.sparepartsrequestId = :sparepartsrequestId
    """)
    int updatePaymentStatusByRequestId(@Param("sparepartsrequestId") Long sparepartsrequestId,
                                       @Param("paymentStatus") String paymentStatus);
}