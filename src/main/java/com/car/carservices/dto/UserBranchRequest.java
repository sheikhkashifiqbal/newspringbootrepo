// src/main/java/com/car/carservices/dto/UserBranchRequest.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserBranchRequest {

    @JsonAlias({"userId", "user_id"})
    private Long userId;

    @JsonAlias({"branchId", "branch_id"})
    private Long branchId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
}
