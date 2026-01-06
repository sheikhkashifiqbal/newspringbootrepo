package com.car.carservices.controller;


import com.car.carservices.dto.*;
import com.car.carservices.service.RateSparepartExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/rate-sparepart-experiences")
public class RateSparepartExperienceController {


private final RateSparepartExperienceService service;


public RateSparepartExperienceController(RateSparepartExperienceService service) {
this.service = service;
}


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public RateSparepartExperienceResponseDTO create(@Valid @RequestBody RateSparepartExperienceRequestDTO dto) {
return service.create(dto);
}


@PutMapping("/{id}")
public RateSparepartExperienceResponseDTO update(
@PathVariable Long id,
@Valid @RequestBody RateSparepartExperienceRequestDTO dto) {
return service.update(id, dto);
}


@GetMapping("/{id}")
public RateSparepartExperienceResponseDTO getById(@PathVariable Long id) {
return service.getById(id);
}


@GetMapping("/by-sparepart/{branchBrandSparepartId}")
public List<RateSparepartExperienceResponseDTO> getBySparepart(
@PathVariable Long branchBrandSparepartId) {
return service.getByBranchBrandSparepartId(branchBrandSparepartId);
}


@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable Long id) {
service.delete(id);
}
}
