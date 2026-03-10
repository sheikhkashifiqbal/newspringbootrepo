package com.car.carservices.service;

import com.car.carservices.dto.StrpVerifyCardRequest;
import com.car.carservices.dto.StrpVerifyCardResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Refund;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StrpVerifyCardService {

    @Value("${stripe.currency:usd}")
    private String currency;

    @Value("${stripe.verify-amount:100}")
    private Long verifyAmount;

    @Value("${stripe.price-id}")
    private String priceId;

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

            // 3) Set default payment method on customer for invoices/subscriptions
            CustomerUpdateParams updateParams = CustomerUpdateParams.builder()
                    .setInvoiceSettings(
                            CustomerUpdateParams.InvoiceSettings.builder()
                                    .setDefaultPaymentMethod(pmId)
                                    .build()
                    )
                    .build();
            customer = customer.update(updateParams);

            // 4) Create a $1 PaymentIntent and confirm it (off_session)
            //    This validates the card can be charged. Then we refund.
            PaymentIntentCreateParams piParams = PaymentIntentCreateParams.builder()
                    .setAmount(verifyAmount) // 100 = $1
                    .setCurrency(currency)
                    .setCustomer(customer.getId())
                    .setPaymentMethod(pmId)
                    .setConfirm(true)
                    .setOffSession(true)
                    .setDescription("Card verification charge (refunded)")
                    .build();

            PaymentIntent pi = PaymentIntent.create(piParams);

            // If PaymentIntent requires action (3DS), you must handle it on client (advanced flow).
            if (!"succeeded".equalsIgnoreCase(pi.getStatus())) {
                return StrpVerifyCardResponse.failed(
                        "Card not verified. PaymentIntent status: " + pi.getStatus()
                );
            }

            // 5) Refund the $1
            RefundCreateParams refundParams = RefundCreateParams.builder()
                    .setPaymentIntent(pi.getId())
                    .build();
            Refund refund = Refund.create(refundParams);

            if (!"succeeded".equalsIgnoreCase(refund.getStatus())) {
                return StrpVerifyCardResponse.failed(
                        "Card charged but refund failed. Refund status: " + refund.getStatus()
                );
            }

            // 6) Create subscription (so we can return stripe_subscription_id as you requested)
            //    This requires stripe.price-id in config (test mode price).
            SubscriptionCreateParams subParams = SubscriptionCreateParams.builder()
                    .setCustomer(customer.getId())
                    .addItem(
                            SubscriptionCreateParams.Item.builder()
                                    .setPrice(priceId)
                                    .build()
                    )
                    .setDefaultPaymentMethod(pmId)
                    .build();

            Subscription subscription = Subscription.create(subParams);

            return StrpVerifyCardResponse.success(
                    customer.getId(),
                    subscription.getId(),
                    pmId,
                    subscription.getStatus()
            );

        } catch (StripeException e) {
            // Stripe API failure
            return StrpVerifyCardResponse.failed("Stripe error: " + e.getMessage());
        } catch (Exception e) {
            return StrpVerifyCardResponse.failed("Server error: " + e.getMessage());
        }
    }
}
