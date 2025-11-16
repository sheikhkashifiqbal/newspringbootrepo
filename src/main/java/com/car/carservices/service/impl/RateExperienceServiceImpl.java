package com.car.carservices.service.impl;

import com.car.carservices.dto.RateExperienceDTO;
import com.car.carservices.entity.RateExperience;
import com.car.carservices.mapper.RateExperienceMapper;
import com.car.carservices.repository.RateExperienceRepository;
import com.car.carservices.repository.UserRegistrationRepository;
import com.car.carservices.service.RateExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RateExperienceServiceImpl implements RateExperienceService {

    @Autowired private RateExperienceRepository repository;
    @Autowired private RateExperienceMapper mapper;
    @Autowired private UserRegistrationRepository userRepo;

    @Override
    public RateExperienceDTO create(RateExperienceDTO dto) {
        RateExperience entity = mapper.toEntity(dto);
        if (entity.getDate() == null) entity.setDate(java.time.LocalDate.now());
        return mapper.toDTO(repository.save(entity));
    }

    @Override public RateExperienceDTO get(Long id) { return mapper.toDTO(repository.findById(id).orElseThrow()); }

    @Override public List<RateExperienceDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RateExperienceDTO update(Long id, RateExperienceDTO dto) {
        RateExperience entity = repository.findById(id).orElseThrow();
        entity.setBranchBrandServiceID(dto.getBranchBrandServiceID()); // ✅ correct
        entity.setUserId(dto.getUserId());
        entity.setStars(dto.getStars());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        return mapper.toDTO(repository.save(entity));
    }

    @Override public void delete(Long id) { repository.deleteById(id); }

    @Override
    public Map<String, Object> getBranchSummary(Long branchId) {
        var ratings = repository.findAllByBranchId(branchId);
        long total = repository.countByBranchId(branchId);

        var fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Map<String, Object>> ratingList = ratings.stream().map(r -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("rate_experienceid", r.getRateExperienceID());
            item.put("date", r.getDate() != null ? r.getDate().format(fmt) : null);
            item.put("user_name", userRepo.findById(r.getUserId()).map(u -> u.getFullName()).orElse("Unknown"));
            item.put("stars", r.getStars());
            item.put("description", r.getDescription());
            return item;
        }).collect(Collectors.toList());

        Map<String, String> percentages = new LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            long count = repository.countByStars(branchId, i);
            double pct = total > 0 ? (count * 100.0 / total) : 0.0;
            percentages.put("star_" + i, String.format("%.0f%%", pct));
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("ratings", ratingList);
        response.putAll(percentages);
        response.put("total_ratings", total);
        return response;
    }
}
