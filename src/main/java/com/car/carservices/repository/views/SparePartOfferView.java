// src/main/java/com/car/carservices/repository/views/SparePartOfferView.java
package com.car.carservices.repository.views;

public interface SparePartOfferView {
    Long   getSparePartsRequestId();
    String getDate();
    String getBranchName();
    String getAddress();
    String getCity();
    String getVin();
    String getSparepartsType();
    String getState();
    String getSparePart();
    String getClassType();
    int    getQty();
    double getPrice();
    String getManagerMobile();
    Long   getId();             // branch_brand_spare_part.id

    // new for detail line id
    Long   getDetailId();       // sprd.id

    String getRequestStatus();
}
