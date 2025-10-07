// com/car/carservices/dto/SparePartsRequestDTO.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class SparePartsRequestDTO {
    private Long sparepartsrequestId;
    private Long userId;
    private Long sparepartsId;
    private Long branchId;       // NEW
    private String date;
    private String vinNumber;
    private String requestStatus;
}
