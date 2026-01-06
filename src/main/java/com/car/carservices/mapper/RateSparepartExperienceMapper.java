package com.car.carservices.mapper;


import com.car.carservices.dto.*;
import com.car.carservices.entity.RateSparepartExperienceEntity;


public class RateSparepartExperienceMapper {


public static RateSparepartExperienceEntity toEntity(RateSparepartExperienceRequestDTO dto) {
RateSparepartExperienceEntity entity = new RateSparepartExperienceEntity();
entity.setBranchBrandSparepartId(dto.getBranchBrandSparepartId());
entity.setDescription(dto.getDescription());
entity.setStars(dto.getStars());
return entity;
}


public static RateSparepartExperienceResponseDTO toDTO(RateSparepartExperienceEntity entity) {
RateSparepartExperienceResponseDTO dto = new RateSparepartExperienceResponseDTO();
dto.setRateExperienceId(entity.getRateExperienceId());
dto.setBranchBrandSparepartId(entity.getBranchBrandSparepartId());
dto.setDescription(entity.getDescription());
dto.setStars(entity.getStars());
return dto;
}
}