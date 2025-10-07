package com.car.carservices.service.impl;

import com.car.carservices.dto.CancelReservationDTO;
import com.car.carservices.entity.CancelReservation;
import com.car.carservices.mapper.CancelReservationMapper;
import com.car.carservices.repository.CancelReservationRepository;
import com.car.carservices.service.CancelReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CancelReservationServiceImpl implements CancelReservationService {

    @Autowired
    private CancelReservationRepository repository;

    @Autowired
    private CancelReservationMapper mapper;

    @Override
    public CancelReservationDTO create(CancelReservationDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public CancelReservationDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<CancelReservationDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CancelReservationDTO update(Long id, CancelReservationDTO dto) {
        CancelReservation entity = repository.findById(id).orElseThrow();
        entity.setReservationId(dto.getReservationId());
        entity.setReason(dto.getReason());
        entity.setReasonDetail(dto.getReasonDetail());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}