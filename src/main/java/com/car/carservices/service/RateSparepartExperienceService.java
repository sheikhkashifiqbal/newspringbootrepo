package com.car.carservices.service;


import com.car.carservices.dto.*;


import java.util.List;


public interface RateSparepartExperienceService {


RateSparepartExperienceResponseDTO create(RateSparepartExperienceRequestDTO dto);


RateSparepartExperienceResponseDTO update(Long id, RateSparepartExperienceRequestDTO dto);


RateSparepartExperienceResponseDTO getById(Long id);


List<RateSparepartExperienceResponseDTO> getByBranchBrandSparepartId(Long branchBrandSparepartId);


void delete(Long id);
}