package com.car.carservices.mapper;

import com.car.carservices.dto.RateExperienceDTO;
import com.car.carservices.entity.RateExperience;
import org.springframework.stereotype.Component;

@Component
public class RateExperienceMapper {

    public RateExperienceDTO toDTO(RateExperience entity) {
        RateExperienceDTO dto = new RateExperienceDTO();
        dto.setRateExperienceID(entity.getRateExperienceID());
        dto.setBranchBrandServiceID(entity.getBranchBrandServiceID()); // ✅ correct
        dto.setUserId(entity.getUserId());
        dto.setStars(entity.getStars());
        dto.setDescription(entity.getDescription());
        dto.setReservationId(entity.getReservationId());
        dto.setDate(entity.getDate());
        return dto;
    }

    public RateExperience toEntity(RateExperienceDTO dto) {
        RateExperience entity = new RateExperience();
        entity.setRateExperienceID(dto.getRateExperienceID());
        entity.setBranchBrandServiceID(dto.getBranchBrandServiceID()); // ✅ correct
        entity.setUserId(dto.getUserId());
        entity.setStars(dto.getStars());
        entity.setDescription(dto.getDescription());
        entity.setReservationId(dto.getReservationId());
        entity.setDate(dto.getDate());

        return entity;
    }
}
