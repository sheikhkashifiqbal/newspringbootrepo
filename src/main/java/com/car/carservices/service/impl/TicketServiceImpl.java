package com.car.carservices.service.impl;

import com.car.carservices.dto.TicketDTO;
import com.car.carservices.entity.Ticket;
import com.car.carservices.mapper.TicketMapper;
import com.car.carservices.repository.TicketRepository;
import com.car.carservices.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private TicketMapper mapper;

    @Override
    public TicketDTO create(TicketDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public TicketDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<TicketDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TicketDTO update(Long id, TicketDTO dto) {
        Ticket entity = repository.findById(id).orElseThrow();
        entity.setDescription(dto.getDescription());
        entity.setAttachImg(dto.getAttachImg());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}