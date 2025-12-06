package com.car.carservices.service;

import com.car.carservices.dto.DisableTimeSlotRequest;
import com.car.carservices.entity.DisableTimeSlot;
import com.car.carservices.repository.DisableTimeSlotRepository;
import com.car.carservices.repository.ReservationCheckRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DisableTimeSlotService {

    private final DisableTimeSlotRepository disableTimeSlotRepository;
    private final ReservationCheckRepository reservationCheckRepository;

    public DisableTimeSlotService(
            DisableTimeSlotRepository disableTimeSlotRepository,
            ReservationCheckRepository reservationCheckRepository) {
        this.disableTimeSlotRepository = disableTimeSlotRepository;
        this.reservationCheckRepository = reservationCheckRepository;
    }

    // CREATE
    @Transactional
    public Map<String, Object> create(DisableTimeSlotRequest req) {

        int count = reservationCheckRepository.countReservations(
                req.getBranchId(),
                req.getTimeSlot(),
                req.getServiceId()
        );

        Map<String, Object> response = new HashMap<>();

        if (count > 0) {
            response.put("message", "Please cancel all reservations");
            return response;
        }

        DisableTimeSlot entity = new DisableTimeSlot();
        entity.setBranchId(req.getBranchId());
        entity.setServiceId(req.getServiceId());
        entity.setTimeSlot(req.getTimeSlot());

        DisableTimeSlot saved = disableTimeSlotRepository.save(entity);

        response.put("id", saved.getDisableTimeSlotId());
        response.put("message", "Disable time slot created successfully");
        return response;
    }

    // GET ALL
    public List<DisableTimeSlot> getAll() {
        return disableTimeSlotRepository.findAll();
    }

    // GET BY ID
    public DisableTimeSlot getById(Long id) {
        return disableTimeSlotRepository.findById(id)
                .orElse(null);
    }

    // UPDATE
    @Transactional
    public Map<String, Object> update(Long id, DisableTimeSlotRequest req) {

        Optional<DisableTimeSlot> opt = disableTimeSlotRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (opt.isEmpty()) {
            response.put("message", "Record not found");
            return response;
        }

        int count = reservationCheckRepository.countReservations(
                req.getBranchId(),
                req.getTimeSlot(),
                req.getServiceId()
        );

        if (count > 0) {
            response.put("message", "Please cancel all reservations");
            return response;
        }

        DisableTimeSlot entity = opt.get();
        entity.setBranchId(req.getBranchId());
        entity.setServiceId(req.getServiceId());
        entity.setTimeSlot(req.getTimeSlot());

        disableTimeSlotRepository.save(entity);

        response.put("message", "Disable time slot updated successfully");
        response.put("id", id);

        return response;
    }

    // DELETE
    @Transactional
    public Map<String, String> delete(Long id) {

        disableTimeSlotRepository.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Disable time slot removed successfully");
        return response;
    }
}
