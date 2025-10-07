package com.car.carservices.controller;

import com.car.carservices.dto.ReservationServiceSparepartDTO;
import com.car.carservices.service.ReservationServiceSparepartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}) // adjust if needed
@RequiredArgsConstructor
@RequestMapping("/api/reservation-service-spareparts")

public class ReservationServiceSparepartController {

    private final ReservationServiceSparepartService service;

    @PostMapping
    public ResponseEntity<ReservationServiceSparepartDTO> create(@Valid @RequestBody ReservationServiceSparepartDTO dto) {
        var created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/reservation-service-spareparts/" + created.getReservationServiceSparepartId()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ReservationServiceSparepartDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/by-reservation/{reservationId}")
    public List<ReservationServiceSparepartDTO> listByReservation(@PathVariable Long reservationId) {
        return service.listByReservation(reservationId);
    }

    @PutMapping("/{id}")
    public ReservationServiceSparepartDTO update(@PathVariable Long id, @Valid @RequestBody ReservationServiceSparepartDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
