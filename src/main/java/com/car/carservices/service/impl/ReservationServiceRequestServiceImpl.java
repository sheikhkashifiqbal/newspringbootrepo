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
        ReservationServiceRequest entity = repository.findById(id).orElseThrow();

        entity.setUserId(dto.getUserId());

        // REPLACED: carId -> brandId + modelId
        entity.setUserId(dto.getUserId());
        entity.setBrandId(dto.getBrandId());
        entity.setModelId(dto.getModelId());

        // keep serviceId active (it exists in your table)
        entity.setServiceId(dto.getServiceId());

        entity.setReservationDate(dto.getReservationDate());
        entity.setReservationTime(dto.getReservationTime());
        entity.setReservationStatus(dto.getReservationStatus());
        // entity.setBranchId(dto.getBranchId()); // if you expose it

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
