// com/car/carservices/entity/SparePartsRequest.java
package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "spare_parts_request")
@Data
public class SparePartsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sparepartsrequest_id")
    private Long sparepartsrequestId; // PK

    // FK to user_registration(id)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // FK to spare_parts(spareparts_id)
    @Column(name = "spareparts_id", nullable = false)
    private Long sparepartsId;

    // NEW: FK to branch(branch_id)
    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "date")
    private String date;

    @Column(name = "vin_number")
    private String vinNumber;

    @Column(name = "request_status")
    private String requestStatus;

    // removed earlier: city, state, category_id
}
