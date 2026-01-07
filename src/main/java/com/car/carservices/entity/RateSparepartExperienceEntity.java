package com.car.carservices.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "rate_sparepart_experience")
public class RateSparepartExperienceEntity {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "rate_experience_id")
private Long rateExperienceId;


@Column(name = "branch_brand_sparepart_id", nullable = false)
private Long branchBrandSparepartId;

@Column(name = "user_id", nullable = false)
private Long userId;

@Column(name = "description", length = 1000)
private String description;


@Column(name = "stars", nullable = false)
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

public void setUserId(Long userId) {
  this.userId = userId;
}

public Long getUserId() {
  return userId;
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