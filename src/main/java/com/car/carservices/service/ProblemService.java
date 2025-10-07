package com.car.carservices.service;

import com.car.carservices.dto.ProblemDTO;
import java.util.List;

public interface ProblemService {
    ProblemDTO create(ProblemDTO dto);
    ProblemDTO get(Long id);
    List<ProblemDTO> getAll();
    ProblemDTO update(Long id, ProblemDTO dto);
    void delete(Long id);
}