package com.car.carservices.controller;

import com.car.carservices.dto.DisableServiceRequest;
import com.car.carservices.service.DisableService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DisableServiceController {

    private final DisableService disableService;

    public DisableServiceController(DisableService disableService) {
        this.disableService = disableService;
    }

    @PostMapping("/disable-service")
    public Map<String, String> disableService(
            @RequestBody DisableServiceRequest request) {

        return disableService.disableOrActivateService(request);
    }
}
