package com.car.carservices.dto;

import java.util.List;

public class BranchBookingStatsDTO {
    private Long company_id;
    private Long branch_id;
    private List<Long> service_ids;
    private Long total_available_qty;   // SUM of qty from branch_brand_service per branch
    private Long total_reserve_service; // COUNT of reservations per branch
    private Double percentage_booking;  // (total_reserve_service / total_available_qty) * 100

    public BranchBookingStatsDTO() {}

    public BranchBookingStatsDTO(Long companyId, Long branchId) {
        this.company_id = companyId;
        this.branch_id = branchId;
    }

    public Long getCompany_id() { return company_id; }
    public void setCompany_id(Long company_id) { this.company_id = company_id; }

    public Long getBranch_id() { return branch_id; }
    public void setBranch_id(Long branch_id) { this.branch_id = branch_id; }

    public List<Long> getService_ids() { return service_ids; }
    public void setService_ids(List<Long> service_ids) { this.service_ids = service_ids; }

    public Long getTotal_available_qty() { return total_available_qty; }
    public void setTotal_available_qty(Long total_available_qty) { this.total_available_qty = total_available_qty; }

    public Long getTotal_reserve_service() { return total_reserve_service; }
    public void setTotal_reserve_service(Long total_reserve_service) { this.total_reserve_service = total_reserve_service; }

    public Double getPercentage_booking() { return percentage_booking; }
    public void setPercentage_booking(Double percentage_booking) { this.percentage_booking = percentage_booking; }
}
