package com.car.carservices.service;

import com.car.carservices.dto.StrpVerifyCardRequest;
import com.car.carservices.dto.StrpVerifyCardResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import org.springframework.stereotype.Service;

@Service
public class StrpVerifyCardService {

    private final String currency;
    private final Long verifyAmount;
    private final String priceId;

    public StrpVerifyCardService() {
        this.currency = System.getenv("STRIPE_CURRENCY") != null ? System.getenv("STRIPE_CURRENCY") : "usd";
        this.verifyAmount = System.getenv("STRIPE_VERIFY_AMOUNT") != null ? Long.parseLong(System.getenv("STRIPE_VERIFY_AMOUNT")) : 100L;
        this.priceId = System.getenv("STRIPE_PRICE_ID");
        
        if (priceId == null || priceId.isBlank()) {
            throw new IllegalStateException("Environment variable STRIPE_PRICE_ID must be set!");
        }

        // Optionally set the Stripe secret key for the Stripe SDK
        String secretKey = System.getenv("STRIPE_SECRET_KEY");
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("Environment variable STRIPE_SECRET_KEY must be set!");
        }
        com.stripe.Stripe.apiKey = secretKey;
    }

    public StrpVerifyCardResponse verifyCard(StrpVerifyCardRequest req) {
        try {
            final String pmId = req.getPaymentMethodId();

            // 1) Create Customer
            CustomerCreateParams.Builder customerParams = CustomerCreateParams.builder();
            if (req.getEmail() != null && !req.getEmail().trim().isEmpty()) {
                customerParams.setEmail(req.getEmail().trim());
            }
            if (req.getCardholderName() != null && !req.getCardholderName().trim().isEmpty()) {
                customerParams.setName(req.getCardholderName().trim());
            }
            Customer customer = Customer.create(customerParams.build());

            // 2) Attach PaymentMethod to Customer
            PaymentMethod pm = PaymentMethod.retrieve(pmId);
            pm.attach(PaymentMethodAttachParams.builder().setCustomer(customer.getId()).build());

            // 3) Set default payment method on customer
            CustomerUpdateParams updateParams = CustomerUpdateParams.builder()
                    .setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder().setDefaultPaymentMethod(pmId).build())
                    .build();
            customer = customer.update(updateParams);

            // 4) Create a $1 PaymentIntent and confirm it (off_session)
            PaymentIntent pi = PaymentIntent.create(PaymentIntentCreateParams.builder()
                    .setAmount(verifyAmount)
                    .setCurrency(currency)
                    .setCustomer(customer.getId())
                    .setPaymentMethod(pmId)
                    .setConfirm(true)
                    .setOffSession(true)
                    .setDescription("Card verification charge (refunded)")
                    .build()
            );

            if (!"succeeded".equalsIgnoreCase(pi.getStatus())) {
                return StrpVerifyCardResponse.failed("Card not verified. PaymentIntent status: " + pi.getStatus());
            }

            // 5) Refund the $1
            Refund refund = Refund.create(RefundCreateParams.builder().setPaymentIntent(pi.getId()).build());
            if (!"succeeded".equalsIgnoreCase(refund.getStatus())) {
                return StrpVerifyCardResponse.failed("Card charged but refund failed. Refund status: " + refund.getStatus());
            }

            // 6) Create subscription
            Subscription subscription = Subscription.create(SubscriptionCreateParams.builder()
                    .setCustomer(customer.getId())
                    .addItem(SubscriptionCreateParams.Item.builder().setPrice(priceId).build())
                    .setDefaultPaymentMethod(pmId)
                    .build()
            );

            return StrpVerifyCardResponse.success(customer.getId(), subscription.getId(), pmId, subscription.getStatus());

        } catch (StripeException e) {
            return StrpVerifyCardResponse.failed("Stripe error: " + e.getMessage());
        } catch (Exception e) {
            return StrpVerifyCardResponse.failed("Server error: " + e.getMessage());
        }
    }
}