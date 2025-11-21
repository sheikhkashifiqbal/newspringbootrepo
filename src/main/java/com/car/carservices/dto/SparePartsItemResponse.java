package com.car.carservices.dto;

import lombok.Data;

@Data
public class SparePartsItemResponse {

    private Long id;
    private Long sparepartsrequest_id;
    private String spare_part;
    private String class_type;
    private Integer qty;
    private Double price;
}
