package com.car.carservices.controller;

import com.car.carservices.dto.ReservationDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import com.car.carservices.service.ReservationFilterServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")

@RequestMapping("/api/reservations")
public class ReservationFilterServiceRequestController {

    @Autowired
    private ReservationFilterServiceRequestService reservationService;

    @PostMapping("/filter")
    public ResponseEntity<List<ReservationServiceRequest>> filterReservations(@RequestBody ReservationDTO filterDTO) {
        return ResponseEntity.ok(reservationService.filterReservations(filterDTO));
    }
}
