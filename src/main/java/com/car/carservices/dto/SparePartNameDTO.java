// SparePartNameDTO.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class SparePartNameDTO {
    private Long id;
    private Long sparepartsId;   // foreign‑key value
    private String sparepartsName;
}
