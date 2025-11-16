package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BranchBrandAggregateResponse {
    @JsonProperty("branch_id")
    private long branchId; 
    @JsonProperty("branch_name")       // snake_case as requested
    private String branchName; 
    private List<String> brandNames;   // list of brands for this branch
    private Double stars;              // nullable if no ratings
    private String branchCoverImg;
    private String logoImg;

    public BranchBrandAggregateResponse() {}

    public BranchBrandAggregateResponse(long branchId,String branchName, List<String> brandNames,
                                        Double stars, String branchCoverImg, String logoImg) {
        
        this.branchId = branchId;                                    this.branchName = branchName;
        this.brandNames = brandNames;
        this.stars = stars;
        this.branchCoverImg = branchCoverImg;
        this.logoImg = logoImg;
    }

    public long getBranchId() { return branchId; }
    public void setBranchId(long branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public List<String> getBrandNames() { return brandNames; }
    public void setBrandNames(List<String> brandNames) { this.brandNames = brandNames; }

    public Double getStars() { return stars; }
    public void setStars(Double stars) { this.stars = stars; }

    public String getBranchCoverImg() { return branchCoverImg; }
    public void setBranchCoverImg(String branchCoverImg) { this.branchCoverImg = branchCoverImg; }

    public String getLogoImg() { return logoImg; }
    public void setLogoImg(String logoImg) { this.logoImg = logoImg; }
}
