// com/car/carservices/dto/BranchDTO.java
package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchDTO {
    private Long branchId;
    private Long companyId;
    private String branchName;
    private String branchCode;
    private String branchManagerName;
    private String branchManagerSurname;
    private String branchAddress;

    /** Google Maps URL or plain address */
    private String location;

    private String loginEmail;
    private String password;
    private String logoImg;
    private String branchCoverImg;
    private String status;

    // persisted on branch
    private Double latitude;
    private Double longitude;

    // NEW FIELDS (additive)
    private String city;     // e.g., "Dubai"
    private String address;  // e.g., "Service Center, Deira"

    // request/response schedule (stored in work_days)
    private List<String> workDays;  // ["monday", ...]
    private String from;            // "HH:mm"
    private String to;              // "HH:mm"
}
