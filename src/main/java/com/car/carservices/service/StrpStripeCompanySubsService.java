package com.car.carservices.service;

import com.car.carservices.dto.StrpStripeCompanySubsRequestDTO;
import com.car.carservices.dto.StrpStripeCompanySubsResponseDTO;

import java.util.List;

public interface StrpStripeCompanySubsService {

    StrpStripeCompanySubsResponseDTO create(StrpStripeCompanySubsRequestDTO dto);

    StrpStripeCompanySubsResponseDTO getById(Long id);

    List<StrpStripeCompanySubsResponseDTO> getAll();

    List<StrpStripeCompanySubsResponseDTO> getByCompanyId(Long companyId);

    StrpStripeCompanySubsResponseDTO update(Long id, StrpStripeCompanySubsRequestDTO dto);

    void delete(Long id);
}
