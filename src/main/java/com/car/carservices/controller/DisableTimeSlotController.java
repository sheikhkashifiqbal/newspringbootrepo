package com.car.carservices.controller;

import com.car.carservices.dto.DisableTimeSlotRequest;
import com.car.carservices.entity.DisableTimeSlot;
import com.car.carservices.service.DisableTimeSlotService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/disable-time-slot-service")
public class DisableTimeSlotController {

    private final DisableTimeSlotService service;

    public DisableTimeSlotController(DisableTimeSlotService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Map<String, Object> create(@RequestBody DisableTimeSlotRequest req) {
        return service.create(req);
    }

    // GET ALL
    @GetMapping
    public List<DisableTimeSlot> getAll() {
        return service.getAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public DisableTimeSlot getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Map<String, Object> update(
            @PathVariable Long id,
            @RequestBody DisableTimeSlotRequest req) {
        return service.update(id, req);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
