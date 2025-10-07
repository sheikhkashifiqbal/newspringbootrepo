package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchSparePartSearchDTO {
    private String vinNumber;             // car VIN entered by user
    private String city;                  // branch location filter
    private String sparePartType;         // e.g. "Filter", "Brake", …
    private String condition;             // "new" or "used"
    private List<String> sparePartNames;  // list of names user is searching for
}