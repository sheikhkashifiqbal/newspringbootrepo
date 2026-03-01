package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SPAvailableSparePartDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("spareparts_id")
    private Long sparepartsId;

    @JsonProperty("spareparts_type")
    private String sparepartsType;

    @JsonProperty("state")
    private String state;

    public SPAvailableSparePartDTO() {}

    public SPAvailableSparePartDTO(Long id, Long sparepartsId, String sparepartsType, String state) {
        this.id = id;
        this.sparepartsId = sparepartsId;
        this.sparepartsType = sparepartsType;
        this.state = state;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSparepartsId() { return sparepartsId; }
    public void setSparepartsId(Long sparepartsId) { this.sparepartsId = sparepartsId; }

    public String getSparepartsType() { return sparepartsType; }
    public void setSparepartsType(String sparepartsType) { this.sparepartsType = sparepartsType; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}