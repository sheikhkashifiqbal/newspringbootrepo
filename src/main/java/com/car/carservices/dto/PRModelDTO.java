package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRModelDTO {
    @JsonProperty("model_id")
    private Long modelId;

    @JsonProperty("model_name")
    private String modelName;

    public PRModelDTO(Long modelId, String modelName) {
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public Long getModelId() { return modelId; }
    public String getModelName() { return modelName; }
}
