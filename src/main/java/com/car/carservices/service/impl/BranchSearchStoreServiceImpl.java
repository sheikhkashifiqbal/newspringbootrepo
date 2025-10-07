// com/car/carservices/service/impl/BranchSearchStoreServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchSearchResultDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.entity.WorkDay;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchSearchStoreService;
import com.car.carservices.util.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchSearchStoreServiceImpl implements BranchSearchStoreService {

    private final BranchSearchRepository branchRepo;
    private final WorkDayRepository workDayRepo;
    private final ReservationCounterRepository resRepo;
    private final BranchBrandServiceLiteRepository bbsRepo;
    private final BrandRepository brandRepo;
    private final ServiceEntityRepository serviceRepo;
    private final RateExperienceRepository rateRepo;

    public BranchSearchStoreServiceImpl(
            BranchSearchRepository branchRepo,
            WorkDayRepository workDayRepo,
            ReservationCounterRepository resRepo,
            BranchBrandServiceLiteRepository bbsRepo,
            BrandRepository brandRepo,
            ServiceEntityRepository serviceRepo,
            RateExperienceRepository rateRepo
    ) {
        this.branchRepo = branchRepo;
        this.workDayRepo = workDayRepo;
        this.resRepo = resRepo;
        this.bbsRepo = bbsRepo;
        this.brandRepo = brandRepo;
        this.serviceRepo = serviceRepo;
        this.rateRepo = rateRepo;
    }

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    private static String nn(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }

    private static double dv(Double x, double ifNull) {
        return x == null ? ifNull : x.doubleValue();
    }

    @Override
    public List<BranchSearchResultDTO> searchBranches(BranchSearchServiceRequestDTO req) {
        // record-style accessors
        String brandName   = nn(req.carBrand());
        String modelName   = nn(req.carModel());
        String serviceName = nn(req.serviceEntity());
        String city        = nn(req.location());
        LocalDate date     = FlexibleDateParser.parse(req.date(), req.dateText());
        String weekday     = WeekdayUtil.weekday(date); // "monday" | null

        List<Branch> candidates = branchRepo.searchBranchesFlexibleNoCity(brandName, modelName, serviceName);

        if (city != null) {
        final String c = city.toLowerCase(java.util.Locale.ROOT);
        candidates = candidates.stream()
            .filter(b -> {
                String loc = b.getLocation();  // assuming entity maps location as String
                return loc != null && loc.toLowerCase(java.util.Locale.ROOT).contains(c);
            })
            .toList();
        }

if (candidates.isEmpty()) return java.util.Collections.emptyList();


        Long brandId = (brandName == null) ? null :
                brandRepo.findByBrandNameIgnoreCase(brandName).map(b -> b.getBrandId()).orElse(null);
        Long serviceId = (serviceName == null) ? null :
                serviceRepo.findByServiceNameIgnoreCase(serviceName).map(s -> s.getServiceId()).orElse(null);

        List<BranchSearchResultDTO> results = new ArrayList<>();

        for (Branch b : candidates) {
            // 1) If date provided, ensure weekday is ACTIVE, else skip branch
            if (date != null && weekday != null) {
                long activeCount = workDayRepo.countActiveOnDay(b.getBranchId(), weekday);
                if (activeCount == 0) continue;
            }

            // 2) Get day's window from work_days; fallback 09:00-18:00 if absent
            LocalTime from = LocalTime.of(9, 0), to = LocalTime.of(18, 0);
            if (weekday != null) {
                Optional<WorkDay> wd = workDayRepo.findByBranch_BranchIdAndWorkingDayIgnoreCase(b.getBranchId(), weekday);
                if (wd.isPresent()) { from = wd.get().getFrom(); to = wd.get().getTo(); }
            }
            List<String> rawSlots = TimeSlotUtil.slotsInclusive(from, to, 30);

            // 3) Capacity-aware filtering (requires date + brandId + serviceId)
            // 3) Capacity-aware filtering (requires date + brandId + serviceId)
            List<String> slots = rawSlots;
            final Double price = null; // we don't read price anymore

            if (date != null && brandId != null && serviceId != null) {
                final int cap = bbsRepo.qty(b.getBranchId(), brandId, serviceId).orElse(0);

                if (cap > 0) {
                    final Long brId = b.getBranchId();
                    final Long svId = serviceId;
                    final LocalDate d = date;

                    slots = rawSlots.stream()
                        .filter(t -> {
                            LocalTime lt = LocalTime.parse(t, HHMM); // convert "HH:mm" to LocalTime
                            return resRepo.countByBranchServiceDateTime(brId, svId, d, lt) < cap;
                        })
                        .collect(Collectors.toList());
                } else {
                    slots = Collections.emptyList();
                }
            }


            // 4) Rating
            Double rating = rateRepo.avgStarsForBranch(b.getBranchId());

            // 5) Distance
            Double distanceKm = DistanceUtil.km(req.currentLat(), req.currentLon(), b.getLatitude(), b.getLongitude());

            // 6) Logo (branch/company)
            String logoUrl = b.getLogoImg();

            results.add(new BranchSearchResultDTO(
                    b.getBranchId(),
                    b.getBranchName(),
                    b.getLocation(),
                    rating,
                    distanceKm,
                    logoUrl,
                    slots,
                    price
            ));
        }

        // 7) Sorting — works whether sortBy is an enum or a string
        // 7) Sorting — distance/rating only
        Object sortByObj = req.sortBy(); // works if enum or string
        if (sortByObj != null) {
            String sortKey = sortByObj.toString().toUpperCase(Locale.ROOT);
            switch (sortKey) {
                case "DISTANCE_CLOSEST" -> results.sort(
                    Comparator.comparingDouble(r -> dv(r.distanceKm(), Double.MAX_VALUE))
                );
                case "DISTANCE_FARTHEST" -> results.sort(
                    Comparator.comparingDouble((BranchSearchResultDTO r) -> dv(r.distanceKm(), -1.0)).reversed()
                );
                //case "RATING_HIGH_TO_LOW" -> results.sort(
                    //Comparator.comparingDouble(r -> dv(r.companyRating(), -1.0)).reversed()
                //);
                case "RATING_LOW_TO_HIGH" -> results.sort(
                    Comparator.comparingDouble(r -> dv(r.companyRating(), Double.MAX_VALUE))
                );
                default -> { /* ignore unknown values */ }
            }
        }

        return results;
    }
}
