package com.car.carservices.mapper;

import com.car.carservices.dto.RateExperienceDTO;
import com.car.carservices.entity.RateExperience;
import org.springframework.stereotype.Component;

@Component
public class RateExperienceMapper {
    public RateExperienceDTO toDTO(RateExperience entity) {
        RateExperienceDTO dto = new RateExperienceDTO();
        dto.setRateExperienceID(entity.getRateExperienceID());
       // dto.setBrachBrandServiceID(entity.getBrachBrandServiceID());
       dto.setBranchBrandServiceID(entity.getBranchBrandServiceID());
        dto.setStars(entity.getStars());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public RateExperience toEntity(RateExperienceDTO dto) {
        RateExperience entity = new RateExperience();
        entity.setRateExperienceID(dto.getRateExperienceID());
        //entity.setBrachBrandServiceID(dto.getBrachBrandServiceID());
        entity.setBranchBrandServiceID(dto.getBranchBrandServiceID());
        entity.setStars(dto.getStars());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}