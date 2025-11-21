package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class SparePartsBranchResponse {

    private Long sparepartsrequest_id;

    // New fields
    private Long brand_id;
    private String brand_name;

    private String date;
    private String branch_name;
    private String address;
    private String city;
    private String VIN;
    private String spareparts_type;
    private String state;
    private String request_status;
    private String manager_mobile;

    private List<SparePartsItemResponse> spare_part;
}
