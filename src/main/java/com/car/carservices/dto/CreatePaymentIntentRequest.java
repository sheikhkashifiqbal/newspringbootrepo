package com.car.carservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePaymentIntentRequest {

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount must be >= 1 (in cents)")
    private Long amount; // cents

    @NotBlank(message = "currency is required")
    private String currency; // "usd", "aed", etc.

    // optional
    private Long sparepartsrequest_id;
    private String description;

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Long getSparepartsrequest_id() { return sparepartsrequest_id; }
    public void setSparepartsrequest_id(Long sparepartsrequest_id) { this.sparepartsrequest_id = sparepartsrequest_id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}