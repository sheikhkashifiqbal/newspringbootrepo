package com.car.carservices.dto;

import java.util.List;

public class PRServicesOnlyResponse {
    private List<PRServiceDTO> services;

    public PRServicesOnlyResponse(List<PRServiceDTO> services) {
        this.services = services;
    }

    public List<PRServiceDTO> getServices() { return services; }
}
