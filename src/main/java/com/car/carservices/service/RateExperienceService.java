package com.car.carservices.service;

import com.car.carservices.dto.RateExperienceDTO;
import java.util.List;
import java.util.Map;

public interface RateExperienceService {
    RateExperienceDTO create(RateExperienceDTO dto);
    RateExperienceDTO get(Long id);
    List<RateExperienceDTO> getAll();
    RateExperienceDTO update(Long id, RateExperienceDTO dto);
    void delete(Long id);

    Map<String, Object> getBranchSummary(Long branchId);
}
