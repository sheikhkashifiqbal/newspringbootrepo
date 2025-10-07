package com.car.carservices.controller;

import com.car.carservices.dto.WorkDayDTO;
import com.car.carservices.service.WorkDayService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work-days")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}) // adjust for your frontend
public class WorkDayController {

    private final WorkDayService service;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<WorkDayDTO> create(@Valid @RequestBody WorkDayDTO dto) {
        WorkDayDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/work-days/" + created.getWorkId())).body(created);
    }

    // --- GET by workId (path) ---
    @GetMapping("/{workId}")
    public WorkDayDTO getById(@PathVariable Long workId) {
        return service.getById(workId);
    }

    // --- GET by branchId (path) ---
    @GetMapping("/by-branch/{branchId}")
    public List<WorkDayDTO> listByBranch(@PathVariable Long branchId) {
        return service.listByBranchId(branchId);
    }

    // --- SEARCH via JSON (your spec): send either workId OR branchId ---
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody WorkDaySearchRequest req) {
        if (req.getWorkId() != null) {
            return ResponseEntity.ok(service.getById(req.getWorkId()));
        }
        if (req.getBranchId() != null) {
            return ResponseEntity.ok(service.listByBranchId(req.getBranchId()));
        }
        return ResponseEntity.badRequest().body("Provide either workId or branchId");
    }

    @Data
    public static class WorkDaySearchRequest {
        private Long workId;
        private Long branchId;
    }

    // --- PARTIAL UPDATE (PUT): only send fields you want to change ---
    @PutMapping("/{workId}")
    public WorkDayDTO updatePartial(@PathVariable Long workId, @RequestBody WorkDayDTO dto) {
        return service.updatePartial(workId, dto);
    }

    // --- DELETE ---
    @DeleteMapping("/{workId}")
    public ResponseEntity<Void> delete(@PathVariable Long workId) {
        service.delete(workId);
        return ResponseEntity.noContent().build();
    }
}
