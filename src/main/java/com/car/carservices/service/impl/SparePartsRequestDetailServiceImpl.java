// src/main/java/com/car/carservices/service/impl/SparePartsRequestDetailServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsRequestDetailDTO;
import com.car.carservices.entity.SparePartsRequestDetail;
import com.car.carservices.mapper.SparePartsRequestDetailMapper;
import com.car.carservices.repository.SparePartsRequestDetailRepository;
import com.car.carservices.service.SparePartsRequestDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SparePartsRequestDetailServiceImpl implements SparePartsRequestDetailService {

    private final SparePartsRequestDetailRepository repo;
    private final SparePartsRequestDetailMapper mapper;

    public SparePartsRequestDetailServiceImpl(SparePartsRequestDetailRepository repo,
                                              SparePartsRequestDetailMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public SparePartsRequestDetailDTO create(SparePartsRequestDetailDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public SparePartsRequestDetailDTO get(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SparePartsRequestDetailDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SparePartsRequestDetailDTO> getByRequestId(Long sparepartsrequestId) {
        return repo.findBySparepartsrequestId(sparepartsrequestId).stream().map(mapper::toDTO).toList();
    }

    @Override
    public SparePartsRequestDetailDTO update(Long id, SparePartsRequestDetailDTO dto) {
        SparePartsRequestDetail e = repo.findById(id).orElseThrow();
        e.setSparepartsrequestId(dto.getSparepartsrequestId());
        e.setSparePart(dto.getSparePart());
        e.setClassType(dto.getClassType());
        e.setQty(dto.getQty());
        e.setPrice(dto.getPrice());
        return mapper.toDTO(repo.save(e));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public long deleteByRequestId(Long sparepartsrequestId) {
        return repo.deleteBySparepartsrequestId(sparepartsrequestId);
    }
}
