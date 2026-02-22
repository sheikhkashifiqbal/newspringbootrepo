package com.car.carservices.dto;

public class StrpVerifyCardResponse {
    private boolean verified;
    private String message;

    private String stripeCustomerId;
    private String stripeSubscriptionId;
    private String paymentMethodId;
    private String status;

    public static StrpVerifyCardResponse failed(String message) {
        StrpVerifyCardResponse r = new StrpVerifyCardResponse();
        r.verified = false;
        r.message = message;
        return r;
    }

    public static StrpVerifyCardResponse success(
            String customerId,
            String subscriptionId,
            String paymentMethodId,
            String status
    ) {
        StrpVerifyCardResponse r = new StrpVerifyCardResponse();
        r.verified = true;
        r.message = "Card verified successfully.";
        r.stripeCustomerId = customerId;
        r.stripeSubscriptionId = subscriptionId;
        r.paymentMethodId = paymentMethodId;
        r.status = status;
        return r;
    }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String stripeCustomerId) { this.stripeCustomerId = stripeCustomerId; }

    public String getStripeSubscriptionId() { return stripeSubscriptionId; }
    public void setStripeSubscriptionId(String stripeSubscriptionId) { this.stripeSubscriptionId = stripeSubscriptionId; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
