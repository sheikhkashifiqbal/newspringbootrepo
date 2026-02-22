package com.car.carservices.dto;

import java.time.LocalDateTime;

public class StrpStripeCompanySubsResponseDTO {

    private Long id;
    private Long companyId;

    private String stripeCustomerId;
    private String stripeSubscriptionId;
    private String paymentMethodId;

    private String status;
    private LocalDateTime createdAt;

    public StrpStripeCompanySubsResponseDTO() {}

    public StrpStripeCompanySubsResponseDTO(Long id, Long companyId, String stripeCustomerId,
                                            String stripeSubscriptionId, String paymentMethodId,
                                            String status, LocalDateTime createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.stripeCustomerId = stripeCustomerId;
        this.stripeSubscriptionId = stripeSubscriptionId;
        this.paymentMethodId = paymentMethodId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String stripeCustomerId) { this.stripeCustomerId = stripeCustomerId; }

    public String getStripeSubscriptionId() { return stripeSubscriptionId; }
    public void setStripeSubscriptionId(String stripeSubscriptionId) { this.stripeSubscriptionId = stripeSubscriptionId; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
