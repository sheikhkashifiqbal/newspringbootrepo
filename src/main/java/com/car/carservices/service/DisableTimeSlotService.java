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

    /**
     * Helper: convert optional String serviceId to Long.
     * Accepts: "1",  " 1 ", "", null.
     * Returns: Long or null (for optional behavior).
     */
    private Long toServiceIdOrNull(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(trimmed);
        } catch (NumberFormatException ex) {
            // If it's not a valid number, treat as "no service filter"
            return null;
        }
    }

    // CREATE
    @Transactional
    public Map<String, Object> create(DisableTimeSlotRequest req) {

        Long branchId = req.getBranchId();
        String timeSlot = req.getTimeSlot();
        Long serviceId = toServiceIdOrNull(req.getServiceId());

        int count = reservationCheckRepository.countReservations(
                branchId,
                timeSlot,
                serviceId
        );

        Map<String, Object> response = new HashMap<>();

        if (count > 0) {
            response.put("message", "Please cancel all reservations");
            return response;
        }

        DisableTimeSlot entity = new DisableTimeSlot();
        entity.setBranchId(branchId);
        entity.setServiceId(serviceId);
        entity.setTimeSlot(timeSlot);

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

        Long branchId = req.getBranchId();
        String timeSlot = req.getTimeSlot();
        Long serviceId = toServiceIdOrNull(req.getServiceId());

        int count = reservationCheckRepository.countReservations(
                branchId,
                timeSlot,
                serviceId
        );

        if (count > 0) {
            response.put("message", "Please cancel all reservations");
            return response;
        }

        DisableTimeSlot entity = opt.get();
        entity.setBranchId(branchId);
        entity.setServiceId(serviceId);
        entity.setTimeSlot(timeSlot);

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
