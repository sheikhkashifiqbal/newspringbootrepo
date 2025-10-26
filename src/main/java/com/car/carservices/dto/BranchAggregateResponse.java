package com.car.carservices.dto;

import java.util.List;

public class BranchAggregateResponse {
    private Long branchId;          // <-- NEW
    private String branchName;
    private List<String> serviceNames;
    private String branchCoverImg;
    private String logoImg;
    private Double stars;

    public BranchAggregateResponse() {}

    // UPDATED: include branchId as first param
    public BranchAggregateResponse(Long branchId,
                                   String branchName,
                                   List<String> serviceNames,
                                   String branchCoverImg,
                                   String logoImg,
                                   Double stars) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.serviceNames = serviceNames;
        this.branchCoverImg = branchCoverImg;
        this.logoImg = logoImg;
        this.stars = stars;
    }

    public Long getBranchId() { return branchId; }      // <-- NEW
    public void setBranchId(Long branchId) { this.branchId = branchId; } // <-- NEW

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public List<String> getServiceNames() { return serviceNames; }
    public void setServiceNames(List<String> serviceNames) { this.serviceNames = serviceNames; }

    public String getBranchCoverImg() { return branchCoverImg; }
    public void setBranchCoverImg(String branchCoverImg) { this.branchCoverImg = branchCoverImg; }

    public String getLogoImg() { return logoImg; }
    public void setLogoImg(String logoImg) { this.logoImg = logoImg; }

    public Double getStars() { return stars; }
    public void setStars(Double stars) { this.stars = stars; }
}
