package com.car.carservices.projection;

public interface BranchServiceRow {
    Long getBranchId();
    String getBranchName();
    String getLogoImg();
    String getBranchCoverImg();
    String getServiceName(); // can be null if branch has no services
}
