package com.car.carservices.dto;
import java.util.List;
public record BranchAvailableSlotDTO(
    Long branchId,
    String branchName,
    String location,
    List<String> availableTimeSlots
) {}
