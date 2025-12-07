package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.service.BranchBrandServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// kept your existing annotations and endpoints
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branch-brand-services")
public class BranchBrandServiceController {

    @Autowired
    private BranchBrandServiceService service;

    @PostMapping
    public ResponseEntity<BranchBrandServiceDTO> create(@RequestBody BranchBrandServiceDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchBrandServiceDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<BranchBrandServiceDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchBrandServiceDTO> update(@PathVariable Long id, @RequestBody BranchBrandServiceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // NEW: bulk status update for all rows matching (branch_id, brand_id)
    @PostMapping("/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@RequestBody StatusUpdateRequest req) {
        int updated = service.updateStatusByBranchAndBrand(req.getBranch_id(), req.getBrand_id(), req.getStatus());
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Record is updated successfully");
        body.put("updated_count", updated);
        return ResponseEntity.ok(body);
    }

    // Tiny request DTO (inner class)
    public static class StatusUpdateRequest {
        private Long branch_id;
        private Long brand_id;
        private String status;

        public Long getBranch_id() { return branch_id; }
        public void setBranch_id(Long branch_id) { this.branch_id = branch_id; }
        public Long getBrand_id() { return brand_id; }
        public void setBrand_id(Long brand_id) { this.brand_id = brand_id; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }


    // NEW: Disable/Enable all services of a branch for a specific service_id
@PostMapping("/disable-branch-service")
public ResponseEntity<Map<String, Object>> disableBranchService(@RequestBody DisableBranchServiceRequest req) {
    int updated = service.updateStatusByBranchAndService(req.getBranch_id(), req.getService_id(), req.getStatus());

    Map<String, Object> body = new HashMap<>();
    body.put("message", "Record updated successfully");
    body.put("updated_count", updated);

    return ResponseEntity.ok(body);
}

// NEW: Small request DTO
public static class DisableBranchServiceRequest {
    private Long branch_id;
    private Long service_id;
    private String status;

    public Long getBranch_id() { return branch_id; }
    public void setBranch_id(Long branch_id) { this.branch_id = branch_id; }

    public Long getService_id() { return service_id; }
    public void setService_id(Long service_id) { this.service_id = service_id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

}
