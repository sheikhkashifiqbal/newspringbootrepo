package com.car.carservices.service.impl;
import com.car.carservices.dto.RateSparepartExperienceResponseDTO;
import com.car.carservices.dto.RateSparepartExperienceRequestDTO;
 
import com.car.carservices.entity.RateSparepartExperienceEntity;
import com.car.carservices.mapper.RateSparepartExperienceMapper;
import com.car.carservices.repository.RateSparepartExperienceRepository;
import com.car.carservices.service.RateSparepartExperienceService;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class RateSparepartExperienceServiceImpl implements RateSparepartExperienceService {


private final RateSparepartExperienceRepository repository;


public RateSparepartExperienceServiceImpl(RateSparepartExperienceRepository repository) {
this.repository = repository;
}


@Override
public RateSparepartExperienceResponseDTO create(RateSparepartExperienceRequestDTO dto) {
RateSparepartExperienceEntity entity = RateSparepartExperienceMapper.toEntity(dto);
return RateSparepartExperienceMapper.toDTO(repository.save(entity));
}


@Override
public RateSparepartExperienceResponseDTO update(Long id, RateSparepartExperienceRequestDTO dto) {
RateSparepartExperienceEntity entity = repository.findById(id)
.orElseThrow(() -> new RuntimeException("Rate experience not found"));


entity.setBranchBrandSparepartId(dto.getBranchBrandSparepartId());
entity.setUserId(dto.getUserId());
entity.setDescription(dto.getDescription());
entity.setStars(dto.getStars());


return RateSparepartExperienceMapper.toDTO(repository.save(entity));
}


@Override
public RateSparepartExperienceResponseDTO getById(Long id) {
return repository.findById(id)
.map(RateSparepartExperienceMapper::toDTO)
.orElseThrow(() -> new RuntimeException("Rate experience not found"));
}


@Override
public List<RateSparepartExperienceResponseDTO> getByBranchBrandSparepartId(Long branchBrandSparepartId) {
return repository.findByBranchBrandSparepartId(branchBrandSparepartId)
.stream()
.map(RateSparepartExperienceMapper::toDTO)
.toList();
}


@Override
public void delete(Long id) {
repository.deleteById(id);
}
}