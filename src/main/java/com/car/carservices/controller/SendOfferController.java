package com.car.carservices.controller;

import com.car.carservices.dto.SendOfferDTO;
import com.car.carservices.service.SendOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/send-offers")
public class SendOfferController {

    @Autowired
    private SendOfferService service;

    @PostMapping
    public ResponseEntity<SendOfferDTO> create(@RequestBody SendOfferDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SendOfferDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<SendOfferDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SendOfferDTO> update(@PathVariable Long id, @RequestBody SendOfferDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
