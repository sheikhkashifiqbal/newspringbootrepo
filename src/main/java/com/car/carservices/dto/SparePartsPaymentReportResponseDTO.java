package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class SparePartsPaymentReportResponseDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("branch_name")
    private String branchName;

    @JsonProperty("total_earning")
    private BigDecimal totalEarning;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("total_comission")
    private BigDecimal totalComission;

    @JsonProperty("net_payment")
    private BigDecimal netPayment;

    @JsonProperty("month")
    private String month; // "02"

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("payment_status")
    private String paymentStatus; // "paid"

    public SparePartsPaymentReportResponseDTO() {}

    public SparePartsPaymentReportResponseDTO(Long companyId, String companyName,
                                              Long branchId, String branchName,
                                              BigDecimal totalEarning, String currency,
                                              BigDecimal totalComission, BigDecimal netPayment,
                                              String month, Integer year, String paymentStatus) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.totalEarning = totalEarning;
        this.currency = currency;
        this.totalComission = totalComission;
        this.netPayment = netPayment;
        this.month = month;
        this.year = year;
        this.paymentStatus = paymentStatus;
    }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public BigDecimal getTotalEarning() { return totalEarning; }
    public void setTotalEarning(BigDecimal totalEarning) { this.totalEarning = totalEarning; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getTotalComission() { return totalComission; }
    public void setTotalComission(BigDecimal totalComission) { this.totalComission = totalComission; }

    public BigDecimal getNetPayment() { return netPayment; }
    public void setNetPayment(BigDecimal netPayment) { this.netPayment = netPayment; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}