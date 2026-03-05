// src/main/java/com/car/carservices/dto/SparePartsRequestDetailBulkPaymentStatusDTO.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class SparePartsRequestDetailBulkPaymentStatusDTO {

    @NotNull
    @JsonProperty("sparepartsrequest_id")
    private Long sparepartsrequestId;

    @NotNull
    @JsonProperty("payment_status")
    private String paymentStatus; // "paid" | "unpaid"

    public Long getSparepartsrequestId() {
        return sparepartsrequestId;
    }

    public void setSparepartsrequestId(Long sparepartsrequestId) {
        this.sparepartsrequestId = sparepartsrequestId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}