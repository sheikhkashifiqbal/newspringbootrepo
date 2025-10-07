package com.car.carservices.dto;

import lombok.Data;

/** All fields nullable so PUT can be partial. */
@Data
public class WorkDayDTO {
    private Long workId;
    private Long branchId;     // relation id
    private String workingDay; // e.g., "Monday"
    private String from;       // "HH:mm" (09:00)
    private String to;         // "HH:mm"
    private String status;     // e.g., ACTIVE/INACTIVE
}
