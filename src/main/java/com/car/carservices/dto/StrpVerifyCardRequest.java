package com.car.carservices.dto;

import jakarta.validation.constraints.NotBlank;

public class StrpVerifyCardRequest {

    @NotBlank
    private String paymentMethodId;

    // Optional but recommended
    private String cardholderName;
    private String email; // optional

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getCardholderName() { return cardholderName; }
    public void setCardholderName(String cardholderName) { this.cardholderName = cardholderName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
