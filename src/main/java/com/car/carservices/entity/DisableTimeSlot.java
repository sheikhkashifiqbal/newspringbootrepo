package com.car.carservices.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disable_time_slot")
public class DisableTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disable_time_slot_id")
    private Long disableTimeSlotId;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "service_id")
    private Long serviceId; // optional

    @Column(name = "time_slot")
    private String timeSlot;

    // getters & setters
    public Long getDisableTimeSlotId() {
        return disableTimeSlotId;
    }

    public void setDisableTimeSlotId(Long disableTimeSlotId) {
        this.disableTimeSlotId = disableTimeSlotId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
