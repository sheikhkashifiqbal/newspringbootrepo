package com.car.carservices.service.impl;

import com.car.carservices.dto.PaymentServicesBranchSummaryDTO;
import com.car.carservices.entity.PaymentServices;
import com.car.carservices.repository.PaymentServicesReportDao;
import com.car.carservices.repository.PaymentServicesRepository;
import com.car.carservices.service.PaymentServicesGenerateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServicesGenerateServiceImpl implements PaymentServicesGenerateService {

    private final PaymentServicesReportDao reportDao;
    private final PaymentServicesRepository paymentRepo;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.06");

    public PaymentServicesGenerateServiceImpl(PaymentServicesReportDao reportDao,
                                              PaymentServicesRepository paymentRepo) {
        this.reportDao = reportDao;
        this.paymentRepo = paymentRepo;
    }

    @Override
    @Transactional
    public List<PaymentServicesBranchSummaryDTO> generateAndStore(String month, Integer year) {

        LocalDate now = LocalDate.now();
        int m = normalizeMonthToInt(month, now.getMonthValue()); // ✅ int month (2)
        int y = (year == null ? now.getYear() : year);

        // 1) Query totals per branch for the month/year
        List<PaymentServicesReportDao.BranchLevelRow> rows = reportDao.fetchBranchTotals(m, y);

        // 2) Insert/Update into payment_services per (branch_id, month(int), year)
        for (var r : rows) {
            BigDecimal earning = nz(r.totalEarning()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal commission = earning.multiply(COMMISSION_RATE).setScale(2, RoundingMode.HALF_UP);

            PaymentServices entity = paymentRepo
                    .findByBranchIdAndMonthAndYear(r.branchId(), m, y)
                    .orElse(null);

            if (entity == null) {
                PaymentServices ps = new PaymentServices();
                ps.setCompanyId(r.companyId());
                ps.setCompanyName(r.companyName());
                ps.setBranchId(r.branchId());
                ps.setBranchName(r.branchName());
                ps.setTotalEarning(earning);
                ps.setCurrency(r.currency());
                ps.setTotalComission(commission);
                ps.setMonth(m);       // ✅ store int
                ps.setYear(y);
                ps.setStatus("unpaid");
                paymentRepo.save(ps);
            } else {
                String status = entity.getStatus() == null ? "" : entity.getStatus().trim().toLowerCase();
                if ("unpaid".equals(status)) {
                    entity.setCompanyId(r.companyId());
                    entity.setCompanyName(r.companyName());
                    entity.setBranchName(r.branchName());
                    entity.setTotalEarning(earning);
                    entity.setCurrency(r.currency());
                    entity.setTotalComission(commission);
                    paymentRepo.save(entity);
                }
            }
        }

        // 3) Build response (month should be "02" in JSON)
        String month2 = String.format("%02d", m);

        List<PaymentServicesBranchSummaryDTO> response = new ArrayList<>(rows.size());
        for (var r : rows) {
            BigDecimal earning2 = nz(r.totalEarning()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal commission2 = earning2.multiply(COMMISSION_RATE).setScale(2, RoundingMode.HALF_UP);

            response.add(new PaymentServicesBranchSummaryDTO(
                    r.companyId(),
                    r.companyName(),
                    r.branchId(),
                    r.branchName(),
                    earning2,
                    r.currency(),
                    commission2,
                    month2,  // ✅ formatted
                    y
            ));
        }

        return response;
    }

    private static int normalizeMonthToInt(String monthStr, int defaultMonth) {
        if (monthStr == null || monthStr.trim().isEmpty()) return defaultMonth;
        String s = monthStr.trim();
        // allow "02" or "2"
        try {
            int m = Integer.parseInt(s);
            if (m < 1 || m > 12) return defaultMonth;
            return m;
        } catch (Exception e) {
            return defaultMonth;
        }
    }

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}