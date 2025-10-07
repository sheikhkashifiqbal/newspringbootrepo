package com.car.carservices.service.impl;

import com.car.carservices.dto.SendOfferDTO;
import com.car.carservices.entity.SendOffer;
import com.car.carservices.mapper.SendOfferMapper;
import com.car.carservices.repository.SendOfferRepository;
import com.car.carservices.service.SendOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendOfferServiceImpl implements SendOfferService {

    @Autowired
    private SendOfferRepository repository;

    @Autowired
    private SendOfferMapper mapper;

    @Override
    public SendOfferDTO create(SendOfferDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public SendOfferDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<SendOfferDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SendOfferDTO update(Long id, SendOfferDTO dto) {
        SendOffer entity = repository.findById(id).orElseThrow();
        entity.setSparepartsrequestId(dto.getSparepartsrequestId());
        entity.setOfferClass(dto.getOfferClass());
        entity.setPrice(dto.getPrice());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
