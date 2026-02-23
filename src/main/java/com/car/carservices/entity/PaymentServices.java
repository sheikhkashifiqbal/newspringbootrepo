package com.car.carservices.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "payment_services",
        uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "month", "year"})
)
public class PaymentServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "total_earning", precision = 14, scale = 2)
    private BigDecimal totalEarning;

    @Column(name = "currency")
    private String currency;

    @Column(name = "total_comission", precision = 14, scale = 2)
    private BigDecimal totalComission;

    // ✅ FIX: store month as INTEGER (e.g., 2)
    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "status")
    private String status;

    // -------- getters/setters --------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}