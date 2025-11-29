package com.car.carservices.service.impl;

import com.car.carservices.dto.ReservationServiceRequestDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import com.car.carservices.mapper.ReservationServiceRequestMapper;
import com.car.carservices.repository.ReservationServiceRequestRepository;
import com.car.carservices.service.ReservationServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceRequestServiceImpl implements ReservationServiceRequestService {

    @Autowired
    private ReservationServiceRequestRepository repository;

    @Autowired
    private ReservationServiceRequestMapper mapper;

    @Override
    public ReservationServiceRequestDTO create(ReservationServiceRequestDTO dto) {
        ReservationServiceRequest entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public ReservationServiceRequestDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<ReservationServiceRequestDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

@Override
public ReservationServiceRequestDTO update(Long id, ReservationServiceRequestDTO dto) {

    ReservationServiceRequest entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

    // Update fields ONLY if they are provided in request (not null)
    if (dto.getUserId() != null) {
        entity.setUserId(dto.getUserId());
    }

    if (dto.getCarId() != null) {
        entity.setCarId(dto.getCarId());
    }

    if (dto.getBrandId() != null) {
        entity.setBrandId(dto.getBrandId());
    }

    if (dto.getModelId() != null) {
        entity.setModelId(dto.getModelId());
    }

    if (dto.getServiceId() != null) {
        entity.setServiceId(dto.getServiceId());
    }

    if (dto.getReservationDate() != null) {
        entity.setReservationDate(dto.getReservationDate());
    }

    if (dto.getReservationTime() != null) {
        entity.setReservationTime(dto.getReservationTime());
    }

    if (dto.getReservationStatus() != null && !dto.getReservationStatus().isBlank()) {
        entity.setReservationStatus(dto.getReservationStatus());
    }

    return mapper.toDTO(repository.save(entity));
}

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
