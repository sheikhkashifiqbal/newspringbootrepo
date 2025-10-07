// com/car/carservices/service/BranchService.java
package com.car.carservices.service;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.dto.BranchPartialUpdateRequest;
import com.car.carservices.entity.Branch;
import com.car.carservices.entity.Company;
import com.car.carservices.entity.WorkDay;
import com.car.carservices.mapper.BranchMapper;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.repository.CompanyRepository;
import com.car.carservices.repository.WorkDayRepository;
import com.car.carservices.util.DayNameUtil;
import com.car.carservices.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BranchService {

    @Autowired private BranchRepository repo;
    @Autowired private BranchMapper mapper;
    @Autowired private WorkDayRepository workDayRepo;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DEFAULT_FROM = "09:00";
    private static final String DEFAULT_TO   = "18:00";

    public List<BranchDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .peek(this::hydrateScheduleFieldsFromDBEnsureDefaults) // NEVER null
                .collect(Collectors.toList());
    }
    public boolean isLoginEmailUnique(String email) {
    return !repo.existsByLoginEmailIgnoreCase(email);
    }

    

    public List<BranchDTO> getByCompany(Long companyId) {
        return repo.findByCompanyCompanyId(companyId).stream()
                .map(mapper::toDTO)
                .peek(this::hydrateScheduleFieldsFromDBEnsureDefaults) // NEVER null
                .collect(Collectors.toList());
    }

    public BranchDTO get(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .map(dto -> { hydrateScheduleFieldsFromDBEnsureDefaults(dto); return dto; }) // NEVER null
                .orElse(null);
    }

    @Transactional
    public BranchDTO save(BranchDTO dto) {
        Branch entity = mapper.toEntity(dto);

        if (dto.getLoginEmail() != null && !isLoginEmailUnique(dto.getLoginEmail())) {
        throw new IllegalArgumentException("Duplicate email address");
        }

           

        // Encode password if present
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }

        // Extract lat/lon from Google Maps URL if not provided directly
        if ((entity.getLatitude() == null || entity.getLongitude() == null) && dto.getLocation() != null) {
            Double[] coords = LocationUtil.extractLatLon(dto.getLocation());
            if (coords != null) {
                entity.setLatitude(coords[0]);
                entity.setLongitude(coords[1]);
            }
        }

        Branch saved = repo.save(entity);

        // Always replace work_days (defaults if missing)
        List<String> days = normalizedOrDefaultDays(dto.getWorkDays());
        String from = (dto.getFrom() != null && !dto.getFrom().isBlank()) ? dto.getFrom() : DEFAULT_FROM;
        String to   = (dto.getTo()   != null && !dto.getTo().isBlank())   ? dto.getTo()   : DEFAULT_TO;
        upsertWorkDays(saved, days, from, to);

        BranchDTO out = mapper.toDTO(saved);
        out.setWorkDays(days);
        out.setFrom(from);
        out.setTo(to);
        return out;
    }

    @Transactional
    public BranchDTO update(Long id, BranchDTO dto) {
        Branch entity = repo.findById(id).orElseThrow();

        entity.setBranchName(dto.getBranchName());
        entity.setBranchCode(dto.getBranchCode());
        entity.setBranchManagerName(dto.getBranchManagerName());
        entity.setBranchManagerSurname(dto.getBranchManagerSurname());
        entity.setBranchAddress(dto.getBranchAddress());
        entity.setLocation(dto.getLocation());
        entity.setLoginEmail(dto.getLoginEmail());
        entity.setPassword(dto.getPassword());
        entity.setLogoImg(dto.getLogoImg());
        entity.setBranchCoverImg(dto.getBranchCoverImg());
        entity.setStatus(dto.getStatus());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        // NEW fields (already supported by your mapper/entity)
        entity.setCity(dto.getCity());
        entity.setAddress(dto.getAddress());

        if (dto.getLoginEmail() != null &&
          repo.existsByLoginEmailIgnoreCaseAndBranchIdNot(dto.getLoginEmail(), id)) {
          throw new IllegalArgumentException("Duplicate email address");
        }
        entity.setLoginEmail(dto.getLoginEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword())); // encode
         }

        if ((entity.getLatitude() == null || entity.getLongitude() == null) && dto.getLocation() != null) {
            Double[] coords = LocationUtil.extractLatLon(dto.getLocation());
            if (coords != null) {
                entity.setLatitude(coords[0]);
                entity.setLongitude(coords[1]);
            }
        }

        Branch saved = repo.save(entity);

        // Always replace work_days (defaults if missing)
        List<String> days = normalizedOrDefaultDays(dto.getWorkDays());
        String from = (dto.getFrom() != null && !dto.getFrom().isBlank()) ? dto.getFrom() : DEFAULT_FROM;
        String to   = (dto.getTo()   != null && !dto.getTo().isBlank())   ? dto.getTo()   : DEFAULT_TO;
        upsertWorkDays(saved, days, from, to);

        BranchDTO out = mapper.toDTO(saved);
        out.setWorkDays(days);
        out.setFrom(from);
        out.setTo(to);
        return out;
    }

    /**
     * PARTIAL UPDATE: apply only non-null fields from the request.
     * Leaves all unspecified fields unchanged.
     */
    @Transactional
    public BranchDTO partialUpdate(Long branchId, BranchPartialUpdateRequest req) {
        Branch b = repo.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found: " + branchId));

        if (req.getLoginEmail() != null) {
            if (repo.existsByLoginEmailIgnoreCaseAndBranchIdNot(req.getLoginEmail(), branchId)) {
                throw new IllegalArgumentException("Duplicate email address");
            }
            b.setLoginEmail(req.getLoginEmail());
        }
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            b.setPassword(passwordEncoder.encode(req.getPassword())); // encode
        }


        if (req.getBranchName() != null) b.setBranchName(req.getBranchName());
        if (req.getBranchCode() != null) b.setBranchCode(req.getBranchCode());

        if (req.getBranchManagerName() != null) b.setBranchManagerName(req.getBranchManagerName());
        if (req.getBranchManagerSurname() != null) b.setBranchManagerSurname(req.getBranchManagerSurname());

        // legacy address vs new address — support both
        if (req.getBranchAddress() != null) b.setBranchAddress(req.getBranchAddress());
        if (req.getAddress() != null) b.setAddress(req.getAddress());

        if (req.getCity() != null) b.setCity(req.getCity());
        if (req.getLocation() != null) b.setLocation(req.getLocation());

        if (req.getLoginEmail() != null) b.setLoginEmail(req.getLoginEmail());
        if (req.getPassword() != null) b.setPassword(req.getPassword()); // keep your existing encoding policy

        if (req.getLogoImg() != null) b.setLogoImg(req.getLogoImg());
        if (req.getBranchCoverImg() != null) b.setBranchCoverImg(req.getBranchCoverImg());
        if (req.getStatus() != null) b.setStatus(req.getStatus());

        if (req.getLatitude() != null) b.setLatitude(req.getLatitude());
        if (req.getLongitude() != null) b.setLongitude(req.getLongitude());

        // Optional: change company
        if (req.getCompanyId() != null) {
            Company company = companyRepository.findById(req.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Company not found: " + req.getCompanyId()));
            b.setCompany(company);
        }

        // If lat/lon still null but location is a Google Maps link, derive coordinates
        if ((b.getLatitude() == null || b.getLongitude() == null) && b.getLocation() != null) {
            Double[] coords = LocationUtil.extractLatLon(b.getLocation());
            if (coords != null) {
                b.setLatitude(coords[0]);
                b.setLongitude(coords[1]);
            }
        }

        Branch saved = repo.save(b);

        // Schedule is not part of partial DTO; we just hydrate defaults for response
        BranchDTO out = mapper.toDTO(saved);
        hydrateScheduleFieldsFromDBEnsureDefaults(out);
        return out;
    }

    @Transactional
    public void delete(Long id) {
        // 1) delete work_days for this branch
        workDayRepo.deleteByBranch_BranchId(id);
        // 2) delete the branch
        repo.deleteById(id);
    }

    /* ------------ helpers ------------ */

    private List<String> normalizedOrDefaultDays(List<String> raw) {
        if (raw == null || raw.isEmpty()) return new ArrayList<>(DayNameUtil.week()); // all active
        List<String> out = new ArrayList<>();
        for (String s : raw) {
            String n = DayNameUtil.normalize(s);
            if (n != null) out.add(n);
        }
        return out.isEmpty() ? new ArrayList<>(DayNameUtil.week()) : out;
    }

    /** Upsert 7 rows in work_days table and set active/inactive + from/to time range. */
    private void upsertWorkDays(Branch branch, List<String> desiredActiveDays, String from, String to) {
        List<String> week = DayNameUtil.week(); // ["monday","tuesday",...]
        Set<String> active = new LinkedHashSet<>();
        if (desiredActiveDays != null) {
            for (String s : desiredActiveDays) {
                String n = DayNameUtil.normalize(s);
                if (n != null) active.add(n);
            }
        }
        if (active.isEmpty()) active.addAll(week); // default: all active

        LocalTime fromT = LocalTime.parse(from, HHMM);
        LocalTime toT   = LocalTime.parse(to,   HHMM);

        for (String day : week) {
            WorkDay wd = workDayRepo
                    .findByBranch_BranchIdAndWorkingDay(branch.getBranchId(), day)
                    .orElseGet(() -> {
                        WorkDay w = new WorkDay();
                        w.setBranch(branch);
                        w.setWorkingDay(day);
                        return w;
                    });

            wd.setFrom(fromT);
            wd.setTo(toT);
            wd.setStatus(active.contains(day) ? "active" : "inactive");
            workDayRepo.save(wd); // update or insert
        }

        // OPTIONAL: clean up any legacy rows with non-canonical day names
        workDayRepo.deleteByBranchAndWorkingDayNotIn(branch.getBranchId(), week);
    }

    /** Always fills workDays/from/to so responses are never null (defaults when no rows). */
    private void hydrateScheduleFieldsFromDBEnsureDefaults(BranchDTO dto) {
        if (dto == null || dto.getBranchId() == null) {
            dto.setWorkDays(new ArrayList<>(DayNameUtil.week()));
            dto.setFrom(DEFAULT_FROM);
            dto.setTo(DEFAULT_TO);
            return;
        }
        List<WorkDay> rows = workDayRepo.findByBranch_BranchId(dto.getBranchId());
        if (rows == null || rows.isEmpty()) {
            dto.setWorkDays(new ArrayList<>(DayNameUtil.week()));
            dto.setFrom(DEFAULT_FROM);
            dto.setTo(DEFAULT_TO);
            return;
        }
        List<String> active = rows.stream()
                .filter(w -> "active".equalsIgnoreCase(w.getStatus()))
                .map(WorkDay::getWorkingDay)
                .toList();
        dto.setWorkDays(active.isEmpty() ? new ArrayList<>(DayNameUtil.week()) : active);

        LocalTime minFrom = rows.stream().map(WorkDay::getFrom).min(LocalTime::compareTo)
                .orElse(LocalTime.parse(DEFAULT_FROM, HHMM));
        LocalTime maxTo   = rows.stream().map(WorkDay::getTo).max(LocalTime::compareTo)
                .orElse(LocalTime.parse(DEFAULT_TO, HHMM));
        dto.setFrom(minFrom.format(HHMM));
        dto.setTo(maxTo.format(HHMM));
    }
}
