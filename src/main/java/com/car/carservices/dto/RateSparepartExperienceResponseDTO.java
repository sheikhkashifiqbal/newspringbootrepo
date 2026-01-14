package com.car.carservices.dto;


public class RateSparepartExperienceResponseDTO {


private Long rateExperienceId;
private Long branchBrandSparepartId;
private Long sparepartsrequestId;

private Long userId;
private String description;
private Integer stars;


public Long getRateExperienceId() {
return rateExperienceId;
}


public void setUserId(Long userId) {
  this.userId = userId;
}

public Long getUserId() {
  return userId;
}

public void setRateExperienceId(Long rateExperienceId) {
this.rateExperienceId = rateExperienceId;
}


public Long getBranchBrandSparepartId() {
return branchBrandSparepartId;
}

public Long getSparepartsrequestId() { return sparepartsrequestId; }
public void setSparepartsrequestId(Long sparepartsrequestId) { this.sparepartsrequestId = sparepartsrequestId; }

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