package com.car.carservices.controller;

import com.car.carservices.dto.CompanyDTO;
import com.car.carservices.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping
    public List<CompanyDTO> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public CompanyDTO get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public CompanyDTO create(@RequestBody CompanyDTO dto) { return service.save(dto); }

    @PutMapping("/{id}")
    public CompanyDTO update(@PathVariable Long id, @RequestBody CompanyDTO dto) {
        dto.setCompanyId(id);
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}