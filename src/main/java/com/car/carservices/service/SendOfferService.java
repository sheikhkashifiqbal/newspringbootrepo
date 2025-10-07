package com.car.carservices.service;

import com.car.carservices.dto.SendOfferDTO;

import java.util.List;

public interface SendOfferService {
    SendOfferDTO create(SendOfferDTO dto);
    SendOfferDTO get(Long id);
    List<SendOfferDTO> getAll();
    SendOfferDTO update(Long id, SendOfferDTO dto);
    void delete(Long id);
}
