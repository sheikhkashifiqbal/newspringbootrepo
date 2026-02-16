package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SparepartsRatingRowDto {

    @JsonProperty("rate_experience_id")
    private Long rateExperienceId;

    private String date;

    @JsonProperty("user_name")
    private String userName;

    private Integer stars;

    private String description;

    @JsonProperty("sparepartsrequest_id")
    private Long sparepartsrequestId;

    public SparepartsRatingRowDto() {}

    // Used by JPQL "select new ..."
    public SparepartsRatingRowDto(Long rateExperienceId,
                                 String date,
                                 String userName,
                                 Integer stars,
                                 String description,
                                 Long sparepartsrequestId) {
        this.rateExperienceId = rateExperienceId;
        this.date = date;
        this.userName = userName;
        this.stars = stars;
        this.description = description;
        this.sparepartsrequestId = sparepartsrequestId;
    }

    public Long getRateExperienceId() {
        return rateExperienceId;
    }
    public void setRateExperienceId(Long rateExperienceId) {
        this.rateExperienceId = rateExperienceId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStars() {
        return stars;
    }
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSparepartsrequestId() {
        return sparepartsrequestId;
    }
    public void setSparepartsrequestId(Long sparepartsrequestId) {
        this.sparepartsrequestId = sparepartsrequestId;
    }
}
