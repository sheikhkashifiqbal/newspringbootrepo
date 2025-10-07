package com.car.carservices.mapper;

import com.car.carservices.dto.ProblemDTO;
import com.car.carservices.entity.Problem;
import org.springframework.stereotype.Component;

@Component
public class ProblemMapper {
    public ProblemDTO toDTO(Problem entity) {
        ProblemDTO dto = new ProblemDTO();
        dto.setProblemId(entity.getProblemId());
        dto.setQuestion(entity.getQuestion());
        return dto;
    }

    public Problem toEntity(ProblemDTO dto) {
        Problem entity = new Problem();
        entity.setProblemId(dto.getProblemId());
        entity.setQuestion(dto.getQuestion());
        return entity;
    }
}