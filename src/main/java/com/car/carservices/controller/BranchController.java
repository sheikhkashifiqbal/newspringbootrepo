// com/car/carservices/controller/BranchController.java
package com.car.carservices.controller;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.dto.BranchPartialUpdateRequest;
import com.car.carservices.service.BranchService;
import com.car.carservices.dto.CompanyBranchesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService service;

    public BranchController(BranchService service) { 
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    @PostMapping("/company")
    public ResponseEntity<List<BranchDTO>> getByCompany(@RequestBody CompanyBranchesRequest req) {
    if (req == null || req.getCompanyId() == null) {
        return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(service.getByCompany(req.getCompanyId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> get(@PathVariable Long id) {
        BranchDTO dto = service.get(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }
    
    @RequestMapping(value = "/update/{branch_id}", method = { RequestMethod.PATCH, RequestMethod.PUT })
    public ResponseEntity<?> partialUpdate(
            @PathVariable("branch_id") Long branchId,
            @RequestBody BranchPartialUpdateRequest req
    ) {
        try {
            BranchDTO updated = service.partialUpdate(branchId, req);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            if ("Duplicate email address".equals(ex.getMessage())) {
                return ResponseEntity.status(409).body(ex.getMessage());
            }
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/check/email")
public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam("email") String email) {
    boolean unique = service.isLoginEmailUnique(email);
    return ResponseEntity.ok(Map.of(
        "field", "login_email",
        "value", email,
        "isUnique", unique,
        "message", unique ? "" : "Duplicate email address"
    ));
}



    @PostMapping
    public ResponseEntity<?> create(@RequestBody BranchDTO dto) {
        try {
            return ResponseEntity.ok(service.save(dto));
        } catch (IllegalArgumentException ex) {
            if ("Duplicate email address".equals(ex.getMessage())) {
                return ResponseEntity.status(409).body(ex.getMessage()); // 409 Conflict
            }
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

/* 
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> update(@PathVariable Long id, @RequestBody BranchDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
