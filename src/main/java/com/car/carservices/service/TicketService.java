package com.car.carservices.service;

import com.car.carservices.dto.TicketDTO;
import java.util.List;

public interface TicketService {
    TicketDTO create(TicketDTO dto);
    TicketDTO get(Long id);
    List<TicketDTO> getAll();
    TicketDTO update(Long id, TicketDTO dto);
    void delete(Long id);
}