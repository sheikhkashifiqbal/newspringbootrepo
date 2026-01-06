package com.car.carservices.dto;


public class RateSparepartExperienceResponseDTO {


private Long rateExperienceId;
private Long branchBrandSparepartId;
private String description;
private Integer stars;


public Long getRateExperienceId() {
return rateExperienceId;
}


public void setRateExperienceId(Long rateExperienceId) {
this.rateExperienceId = rateExperienceId;
}


public Long getBranchBrandSparepartId() {
return branchBrandSparepartId;
}


public void setBranchBrandSparepartId(Long branchBrandSparepartId) {
this.branchBrandSparepartId = branchBrandSparepartId;
}


public String getDescription() {
return description;
}


public void setDescription(String description) {
this.description = description;
}


public Integer getStars() {
return stars;
}


public void setStars(Integer stars) {
this.stars = stars;
}
}