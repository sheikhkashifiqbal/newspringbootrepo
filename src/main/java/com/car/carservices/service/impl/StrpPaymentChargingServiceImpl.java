package com.car.carservices.service.impl;

import com.car.carservices.dto.StrpChargePaymentServicesRequestDTO;
import com.car.carservices.dto.StrpPaymentServicesResponseDTO;
import com.car.carservices.entity.PaymentServices;
import com.car.carservices.entity.StrpStripeCompanySubs;
import com.car.carservices.repository.StrpPaymentServicesRepository;
import com.car.carservices.repository.StrpStripeCompanySubsRepository;
import com.car.carservices.service.StrpPaymentChargingService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StrpPaymentChargingServiceImpl implements StrpPaymentChargingService {

    private final StrpPaymentServicesRepository paymentRepo;
    private final StrpStripeCompanySubsRepository subsRepo;

    public StrpPaymentChargingServiceImpl(
            StrpPaymentServicesRepository paymentRepo,
            StrpStripeCompanySubsRepository subsRepo
    ) {
        this.paymentRepo = paymentRepo;
        this.subsRepo = subsRepo;
    }

    @Override
    @Transactional
    public List<StrpPaymentServicesResponseDTO> chargeUnpaidCommissions(
            StrpChargePaymentServicesRequestDTO req) {

        LocalDate now = LocalDate.now();
        int month = (req == null || req.getMonth() == null)
                ? now.getMonthValue()
                : req.getMonth();

        int year = (req == null || req.getYear() == null)
                ? now.getYear()
                : req.getYear();

        // 1️⃣ Get unpaid rows
        List<PaymentServices> unpaidRows =
                paymentRepo.findByMonthAndYearAndStatusIgnoreCase(month, year, "unpaid");

        List<StrpPaymentServicesResponseDTO> paidList = new ArrayList<>();

        for (PaymentServices ps : unpaidRows) {

            Long companyId = ps.getCompanyId();
            if (companyId == null) continue;

            // ✅ UPDATED: use your existing repository method
            List<StrpStripeCompanySubs> subsList =
                    subsRepo.findByCompany_CompanyId(companyId);

            if (subsList == null || subsList.isEmpty()) {
                continue; // company not subscribed
            }

            // ✅ take latest record (last one)
            StrpStripeCompanySubs subs = subsList.get(subsList.size() - 1);

            // optional: check subscription active
            if (subs.getStatus() != null &&
                    !"active".equalsIgnoreCase(subs.getStatus())) {
                continue;
            }

            BigDecimal commission = ps.getTotalComission();
            if (commission == null || commission.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            String currency = ps.getCurrency();
            if (currency == null || currency.isBlank()) {
                continue;
            }

            String stripeCurrency = normalizeCurrency(currency);
            long amountSmallestUnit = toSmallestUnit(commission, stripeCurrency);

            if (amountSmallestUnit <= 0) continue;

            String idempotencyKey =
                    "ps-" + ps.getId() + "-" + month + "-" + year;

            try {
                PaymentIntentCreateParams params =
                        PaymentIntentCreateParams.builder()
                                .setAmount(amountSmallestUnit)
                                .setCurrency(stripeCurrency)
                                .setCustomer(subs.getStripeCustomerId())
                                .setPaymentMethod(subs.getPaymentMethodId())
                                .setOffSession(true)
                                .setConfirm(true)
                                .setDescription(
                                        "Monthly commission charge company="
                                                + companyId + " month=" + month + " year=" + year)
                                .build();

                PaymentIntent intent = PaymentIntent.create(
                        params,
                        com.stripe.net.RequestOptions.builder()
                                .setIdempotencyKey(idempotencyKey)
                                .build()
                );

                // ✅ mark paid only if succeeded
                if ("succeeded".equalsIgnoreCase(intent.getStatus())) {
                    ps.setStatus("paid");
                    paymentRepo.save(ps);
                    paidList.add(toDto(ps));
                }

            } catch (StripeException ex) {
                // keep unpaid if failed
                // you may log: log.error("Stripe error", ex);
            }
        }

        return paidList;
    }

    // ================= helper methods =================

    private String normalizeCurrency(String currency) {
        String c = currency.trim().toLowerCase(Locale.ROOT);

        if (c.equals("$") || c.equals("dollar") || c.equals("usd")) return "usd";
        if (c.equals("aed") || c.equals("dirham")) return "aed";
        if (c.equals("eur") || c.equals("euro")) return "eur";
        if (c.equals("gbp") || c.equals("pound")) return "gbp";

        return c;
    }

    private long toSmallestUnit(BigDecimal amount, String currency) {
        int fractionDigits = isZeroDecimal(currency) ? 0 : 2;
        BigDecimal scaled = amount.setScale(fractionDigits, RoundingMode.HALF_UP);
        return scaled.movePointRight(fractionDigits).longValueExact();
    }

    private boolean isZeroDecimal(String currency) {
        return "jpy".equalsIgnoreCase(currency)
                || "krw".equalsIgnoreCase(currency);
    }

private StrpPaymentServicesResponseDTO toDto(PaymentServices ps) {
    StrpPaymentServicesResponseDTO dto = new StrpPaymentServicesResponseDTO();
    dto.setCompanyId(ps.getCompanyId());
    dto.setBranchId(ps.getBranchId());
    dto.setCompanyName(ps.getCompanyName());
    dto.setBranchName(ps.getBranchName());
    dto.setTotalEarning(ps.getTotalEarning());
    dto.setCurrency(ps.getCurrency());
    dto.setTotalComission(ps.getTotalComission());
    dto.setMonth(ps.getMonth());
    dto.setYear(ps.getYear());
    dto.setStatus(ps.getStatus());
    dto.setId(ps.getId()); // primary key id
    return dto;
}
}