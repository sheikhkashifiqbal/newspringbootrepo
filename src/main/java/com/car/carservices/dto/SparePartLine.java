// src/main/java/com/car/carservices/dto/SparePartLine.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SparePartLine(
    @JsonProperty("id")                    Long id,                    // sprd.id
    @JsonProperty("sparepartsrequest_id")  Long sparepartsrequestId,   // sprd.sparepartsrequest_id
    @JsonProperty("spare_part")            String sparePart,           // sprd.spare_part
    @JsonProperty("class_type")            String classType,           // sprd.class_type
    @JsonProperty("qty")                   int qty,                    // sprd.qty
    @JsonProperty("price")                 double price                // sprd.price
) {}