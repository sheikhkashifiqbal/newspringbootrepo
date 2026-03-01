package com.car.carservices.service;

import com.car.carservices.dto.CreatePaymentIntentRequest;
import com.car.carservices.dto.CreatePaymentIntentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class StripePaymentIntentService {

    @Value("${stripe.default-currency:usd}")
    private String defaultCurrency;

    @Value("${stripe.statement-descriptor:}")
    private String statementDescriptor;

    public CreatePaymentIntentResponse createPaymentIntent(CreatePaymentIntentRequest req) throws StripeException {
        String currency = (req.getCurrency() == null || req.getCurrency().trim().isEmpty())
                ? defaultCurrency
                : req.getCurrency().trim().toLowerCase(Locale.ROOT);

        // PaymentIntent for card payments
        PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
                .setAmount(req.getAmount())
                .setCurrency(currency)
                .addPaymentMethodType("card");

        if (req.getDescription() != null && !req.getDescription().trim().isEmpty()) {
            builder.setDescription(req.getDescription().trim());
        }

        // Optional: attach metadata to track request
        if (req.getSparepartsrequest_id() != null) {
            builder.putMetadata("sparepartsrequest_id", String.valueOf(req.getSparepartsrequest_id()));
        }

        // Optional: statement descriptor (Stripe has length/character restrictions)
        if (statementDescriptor != null && !statementDescriptor.trim().isEmpty()) {
            // Only set if you are sure it matches Stripe rules; otherwise remove.
            builder.setStatementDescriptor(statementDescriptor.trim());
        }

        PaymentIntent pi = PaymentIntent.create(builder.build());

        return new CreatePaymentIntentResponse(
                pi.getClientSecret(),
                pi.getId(),
                pi.getStatus()
        );
    }
}