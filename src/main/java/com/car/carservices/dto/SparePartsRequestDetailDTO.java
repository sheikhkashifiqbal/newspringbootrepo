// src/main/java/com/car/carservices/dto/SparePartsRequestDetailDTO.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SparePartsRequestDetailDTO {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("sparepartsrequest_id")
    private Long sparepartsrequestId;

    @NotNull
    @JsonProperty("spare_part")
    private String sparePart;

    @JsonProperty("class_type")
    private String classType;

    @NotNull @Min(0)
    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("currency")
    private String currency;

    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    
    @JsonProperty("payment_status")
    private String paymentStatus;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSparepartsrequestId() { return sparepartsrequestId; }
    public void setSparepartsrequestId(Long sparepartsrequestId) { this.sparepartsrequestId = sparepartsrequestId; }
    public String getSparePart() { return sparePart; }
    public void setSparePart(String sparePart) { this.sparePart = sparePart; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}

