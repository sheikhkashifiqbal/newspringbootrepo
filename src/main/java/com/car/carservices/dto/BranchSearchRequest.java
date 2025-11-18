// dto/BranchSearchRequest.java
package com.car.carservices.dto;
import lombok.Data;

@Data
public class BranchSearchRequest {
    private Long carBrand;
    private Long carModel;
    private Long serviceEntity;
    private String date;
    private String location;
    private Double currentLon;
    private Double currentLat;
    private String sortBy;
}
