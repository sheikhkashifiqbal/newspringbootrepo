package com.car.carservices.mapper;

import com.car.carservices.dto.TicketDTO;
import com.car.carservices.entity.Problem;
import com.car.carservices.entity.Ticket;
import com.car.carservices.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    @Autowired
    private ProblemRepository problemRepository;

    public TicketDTO toDTO(Ticket entity) {
        TicketDTO dto = new TicketDTO();
        dto.setTicketId(entity.getTicketId());
        dto.setProblemId(entity.getProblem().getProblemId());
        dto.setDescription(entity.getDescription());
        dto.setAttachImg(entity.getAttachImg());
        return dto;
    }

    public Ticket toEntity(TicketDTO dto) {
        Ticket entity = new Ticket();
        entity.setTicketId(dto.getTicketId());
        Problem problem = problemRepository.findById(dto.getProblemId()).orElseThrow();
        entity.setProblem(problem);
        entity.setDescription(dto.getDescription());
        entity.setAttachImg(dto.getAttachImg());
        return entity;
    }
}