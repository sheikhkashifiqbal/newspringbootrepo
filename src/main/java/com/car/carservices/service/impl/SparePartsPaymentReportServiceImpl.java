package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsPaymentReportResponseDTO;
import com.car.carservices.repository.SparePartsPaymentReportDao;
import com.car.carservices.service.SparePartsPaymentReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SparePartsPaymentReportServiceImpl implements SparePartsPaymentReportService {

    private final SparePartsPaymentReportDao dao;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.06");

    public SparePartsPaymentReportServiceImpl(SparePartsPaymentReportDao dao) {
        this.dao = dao;
    }

    @Override
    public List<SparePartsPaymentReportResponseDTO> generate(Integer month, Integer year) {

        LocalDate now = LocalDate.now();
        int m = (month == null ? now.getMonthValue() : month);
        int y = (year == null ? now.getYear() : year);

        // format month as "02"
        String month2 = String.format("%02d", m);

        List<SparePartsPaymentReportDao.Row> rows = dao.fetchBranchTotals(m, y);

        List<SparePartsPaymentReportResponseDTO> response = new ArrayList<>(rows.size());

        for (var r : rows) {
            BigDecimal earning = nz(r.totalEarning()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal commission = earning.multiply(COMMISSION_RATE).setScale(2, RoundingMode.HALF_UP);
            BigDecimal net = earning.subtract(commission).setScale(2, RoundingMode.HALF_UP);

            response.add(new SparePartsPaymentReportResponseDTO(
                    r.companyId(),
                    r.companyName(),
                    r.branchId(),
                    r.branchName(),
                    earning,
                    r.currency(),
                    commission,
                    net,
                    month2,
                    y,
                    "paid" // because we filter paid only
            ));
        }

        return response;
    }

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}