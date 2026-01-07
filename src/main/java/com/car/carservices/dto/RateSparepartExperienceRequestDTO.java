package com.car.carservices.dto;


import jakarta.validation.constraints.*;


public class RateSparepartExperienceRequestDTO {


@NotNull
private Long branchBrandSparepartId;
@NotNull
private Long userId;

@Size(max = 1000)
private String description;


@NotNull
@Min(1)
@Max(5)
private Integer stars;


public Long getBranchBrandSparepartId() {
return branchBrandSparepartId;
}


public void setBranchBrandSparepartId(Long branchBrandSparepartId) {
this.branchBrandSparepartId = branchBrandSparepartId;
}

public void setUserId(Long userId) {
  this.userId = userId;
}

public Long getUserId() {
  return userId;
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