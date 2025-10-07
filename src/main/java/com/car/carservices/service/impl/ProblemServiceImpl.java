package com.car.carservices.service.impl;

import com.car.carservices.dto.ProblemDTO;
import com.car.carservices.entity.Problem;
import com.car.carservices.mapper.ProblemMapper;
import com.car.carservices.repository.ProblemRepository;
import com.car.carservices.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository repository;

    @Autowired
    private ProblemMapper mapper;

    @Override
    public ProblemDTO create(ProblemDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ProblemDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<ProblemDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProblemDTO update(Long id, ProblemDTO dto) {
        Problem entity = repository.findById(id).orElseThrow();
        entity.setQuestion(dto.getQuestion());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}