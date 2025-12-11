package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.dto.DisableServiceRequest;
import com.car.carservices.entity.BranchBrandService;
import com.car.carservices.mapper.BranchBrandServiceMapper;
import com.car.carservices.repository.DisableServiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DisableService {

    private final DisableServiceRepository repo;
    private final BranchBrandServiceMapper mapper;

    public DisableService(DisableServiceRepository repo,
                                 BranchBrandServiceMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    private boolean hasFutureReservations(Long branchId, Long serviceId) {
        return repo.countFutureReservations(branchId, serviceId) > 0;
    }

    @Transactional
    public Map<String, Object> create(DisableServiceRequest req) {
        Map<String, Object> body = new HashMap<>();

        if (hasFutureReservations(req.getBranchId(), req.getServiceId())) {
            body.put("message", "Please cancel all reservations");
            return body;
        }

        List<BranchBrandService> list =
                repo.findByBranch_BranchIdAndService_ServiceId(req.getBranchId(), req.getServiceId());

        if (list.isEmpty()) {
            body.put("message", "No matching records found");
            return body;
        }

        for (BranchBrandService e : list) {
            e.setStatus(req.getStatus().toLowerCase());
        }
        repo.saveAll(list);

        body.put("message", "Status updated successfully");
        body.put("updated_count", list.size());
        return body;
    }

    public List<BranchBrandServiceDTO> getAll() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toDTO)
                   .toList();
    }

    public BranchBrandServiceDTO getById(Long id) {
        return repo.findById(id)
                   .map(mapper::toDTO)
                   .orElse(null);
    }

    @Transactional
    public Map<String, Object> update(Long id, DisableServiceRequest req) {
        Map<String, Object> body = new HashMap<>();

        BranchBrandService entity = repo.findById(id).orElse(null);
        if (entity == null) {
            body.put("message", "Record not found");
            return body;
        }

        if (hasFutureReservations(req.getBranchId(), req.getServiceId())) {
            body.put("message", "Please cancel all reservations");
            return body;
        }

        entity.setStatus(req.getStatus().toLowerCase());
        repo.save(entity);

        body.put("message", "Record updated successfully");
        body.put("id", id);
        return body;
    }

    @Transactional
    public Map<String, String> delete(Long id) {
        Map<String, String> body = new HashMap<>();

        if (!repo.existsById(id)) {
            body.put("message", "Record not found");
            return body;
        }

        repo.deleteById(id);
        body.put("message", "Record deleted successfully");
        return body;
    }
}
