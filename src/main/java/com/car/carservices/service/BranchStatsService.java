package com.car.carservices.service;

import com.car.carservices.dto.BranchBookingStatsDTO;
import com.car.carservices.repository.BranchStatsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

@Service
public class BranchStatsService {

    private final BranchStatsRepository statsRepo;

    public BranchStatsService(BranchStatsRepository statsRepo) {
        this.statsRepo = statsRepo;
    }

    public List<BranchBookingStatsDTO> getBranchBookingStats(Long companyId) {
        // 1) Base branches (company_id, branch_id)
        List<Object[]> base = statsRepo.fetchAllBranches(companyId);

        // 2) qty per branch
        Map<Long, Long> qtyByBranch = new HashMap<>();
        for (Object[] row : statsRepo.fetchTotalQtyPerBranch(companyId)) {
            Long branchId = row[0] != null ? ((Number) row[0]).longValue() : null;
            Long totalQty  = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            qtyByBranch.put(branchId, totalQty);
        }

        // 3) reservation count per branch
        Map<Long, Long> reserveByBranch = new HashMap<>();
        for (Object[] row : statsRepo.fetchReservationCountPerBranch(companyId)) {
            Long branchId = row[0] != null ? ((Number) row[0]).longValue() : null;
            Long totalReserve = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            reserveByBranch.put(branchId, totalReserve);
        }

        // 4) service_ids per branch
        // 4) service_ids per branch  (robust to driver returning java.sql.Array or Object[])
        Map<Long, List<Long>> servicesByBranch = new HashMap<>();
        for (Object[] row : statsRepo.fetchServiceIdsPerBranch(companyId)) {
            Long branchId = row[0] != null ? ((Number) row[0]).longValue() : null;
            Object rawArray = row[1]; // may be null, java.sql.Array, Long[], or Object[]

            List<Long> ids = new ArrayList<>();
            if (rawArray != null) {
                try {
                    if (rawArray instanceof java.sql.Array sqlArr) {
                        Object arrObj = sqlArr.getArray(); // could be Long[] or Object[]
                        if (arrObj instanceof Long[] longArr) {
                            ids.addAll(Arrays.asList(longArr));
                        } else if (arrObj instanceof Object[] objArr) {
                            for (Object o : objArr) {
                                if (o != null) ids.add(((Number) o).longValue());
                            }
                        }
                    } else if (rawArray instanceof Long[] longArr) {
                        ids.addAll(Arrays.asList(longArr));
                    } else if (rawArray instanceof Object[] objArr) {
                        for (Object o : objArr) {
                            if (o != null) ids.add(((Number) o).longValue());
                        }
                    } else {
                        // As a last resort, try to parse comma-separated text
                        String s = String.valueOf(rawArray).trim();
                        if (!s.isEmpty()) {
                            // Strip braces if it looks like "{1,2,3}"
                            s = s.replace("{", "").replace("}", "");
                            for (String token : s.split(",")) {
                                String t = token.trim();
                                if (!t.isEmpty()) ids.add(Long.parseLong(t));
                            }
                        }
                    }
                } catch (Exception ignored) {
                    // If anything goes wrong, leave ids empty for this branch
                }
            }
            servicesByBranch.put(branchId, ids);
        }

        // 5) Merge into DTOs
        List<BranchBookingStatsDTO> out = new ArrayList<>();
        for (Object[] row : base) {
            Long compId   = row[0] != null ? ((Number) row[0]).longValue() : null;
            Long branchId = row[1] != null ? ((Number) row[1]).longValue() : null;

            BranchBookingStatsDTO dto = new BranchBookingStatsDTO(compId, branchId);
            dto.setService_ids(servicesByBranch.getOrDefault(branchId, Collections.emptyList()));
            Long totalQty = qtyByBranch.getOrDefault(branchId, 0L);
            Long totalReserve = reserveByBranch.getOrDefault(branchId, 0L);
            dto.setTotal_available_qty(totalQty);
            dto.setTotal_reserve_service(totalReserve);

            // percentage = (reserve / qty) * 100, safe divide
            double pct = 0.0;
            if (totalQty != null && totalQty > 0) {
                BigDecimal bd = BigDecimal.valueOf(totalReserve)
                        .divide(BigDecimal.valueOf(totalQty), 6, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP);
                pct = bd.doubleValue();
            }
            dto.setPercentage_booking(pct);

            out.add(dto);
        }

        return out;
    }
}
