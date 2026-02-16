package com.car.carservices.service;

import com.car.carservices.dto.SparepartsRatingResponse;
import com.car.carservices.dto.SparepartsRatingRowDto;
import com.car.carservices.entity.RateSparepartExperienceEntity;
import com.car.carservices.repository.RateSparepartExperienceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SparepartsRatingService {

    private final RateSparepartExperienceRepository rateRepo;

    public SparepartsRatingService(RateSparepartExperienceRepository rateRepo) {
        this.rateRepo = rateRepo;
    }

    public SparepartsRatingResponse getRatingsByBranchId(Long branchId) {

        List<Object[]> raw = rateRepo.findRatingsRawByBranchId(branchId);
        long total = rateRepo.totalCountForBranch(branchId);

        List<SparepartsRatingRowDto> rows = new ArrayList<>();
        for (Object[] r : raw) {
            RateSparepartExperienceEntity e = (RateSparepartExperienceEntity) r[0];
            String userName = (String) r[1];
            Long sparepartsrequestId = (Long) r[2];

            SparepartsRatingRowDto dto = new SparepartsRatingRowDto();
            dto.setRateExperienceId(e.getRateExperienceId());
            dto.setUserName(userName);
            dto.setStars(e.getStars());
            dto.setDescription(e.getDescription());
            dto.setSparepartsrequestId(sparepartsrequestId);

            // ✅ IMPORTANT: set date from your entity field/getter:
            // Pick ONE of these lines (whichever exists in your entity), and delete the others.

            // dto.setDate(formatDate(e.getDate()));          // if you have getDate()
            // dto.setDate(formatDate(e.getCreatedAt()));     // if you have getCreatedAt()
            // dto.setDate(formatDate(e.getRatingDate()));    // if you have getRatingDate()
            // dto.setDate(formatDate(e.getCreated_date()));  // if you have getCreated_date()

            // TEMP fallback (so it doesn't crash if you haven't set it yet):
            dto.setDate("");

            rows.add(dto);
        }

        Map<Integer, Long> counts = new HashMap<>();
        for (Object[] c : rateRepo.countByStarsForBranch(branchId)) {
            Integer stars = (Integer) c[0];
            Long cnt = (Long) c[1];
            if (stars != null) counts.put(stars, cnt);
        }

        SparepartsRatingResponse resp = new SparepartsRatingResponse();
        resp.setRatings(rows);
        resp.setTotalRatings(total);

        resp.setStar1(toPercent(counts.getOrDefault(1, 0L), total));
        resp.setStar2(toPercent(counts.getOrDefault(2, 0L), total));
        resp.setStar3(toPercent(counts.getOrDefault(3, 0L), total));
        resp.setStar4(toPercent(counts.getOrDefault(4, 0L), total));
        resp.setStar5(toPercent(counts.getOrDefault(5, 0L), total));

        return resp;
    }

    private String toPercent(long part, long total) {
        if (total <= 0) return "0%";
        long pct = (part * 100) / total;
        return pct + "%";
    }

    private String formatDate(Object value) {
        if (value == null) return "";

        DateTimeFormatter out = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (value instanceof String s) {
            // If your DB already stores "14.01.2026", keep it.
            // If it stores "2026-01-14", try parse:
            try {
                LocalDate d = LocalDate.parse(s);
                return d.format(out);
            } catch (Exception ignored) {
                return s;
            }
        }

        if (value instanceof LocalDate d) return d.format(out);
        if (value instanceof LocalDateTime dt) return dt.toLocalDate().format(out);

        return String.valueOf(value);
    }
}
