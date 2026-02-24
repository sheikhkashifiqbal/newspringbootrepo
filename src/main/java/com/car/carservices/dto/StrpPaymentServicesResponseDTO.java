package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class StrpPaymentServicesResponseDTO {

    // Removed payment_id because your PaymentServices entity doesn't have it
    // Removed created_at because your PaymentServices entity doesn't have it

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("branch_name")
    private String branchName;

    @JsonProperty("total_earning")
    private BigDecimal totalEarning;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("total_comission")
    private BigDecimal totalComission;

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("status")
    private String status;

    // keep DB primary key id (if your entity has getId())
    @JsonProperty("id")
    private Long id;

    public StrpPaymentServicesResponseDTO() {}

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public BigDecimal getTotalEarning() { return totalEarning; }
    public void setTotalEarning(BigDecimal totalEarning) { this.totalEarning = totalEarning; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getTotalComission() { return totalComission; }
    public void setTotalComission(BigDecimal totalComission) { this.totalComission = totalComission; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}