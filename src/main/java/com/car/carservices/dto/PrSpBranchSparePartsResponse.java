package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PrSpBranchSparePartsResponse {

    @JsonProperty("branch_address")
    private String branchAddress;

    @JsonProperty("city")
    private String city;

    @JsonProperty("location")
    private String location;

    @JsonProperty("manager_mobile")
    private String managerMobile;

    @JsonProperty("manager_phone")
    private String managerPhone;

    @JsonProperty("brands")
    private List<BrandItem> brands;

    public PrSpBranchSparePartsResponse(
            String branchAddress, String city, String location,
            String managerMobile, String managerPhone,
            List<BrandItem> brands) {
        this.branchAddress = branchAddress;
        this.city = city;
        this.location = location;
        this.managerMobile = managerMobile;
        this.managerPhone = managerPhone;
        this.brands = brands;
    }

    public List<BrandItem> getBrands() {
        return brands;
    }

    // ----------------- Nested classes -----------------
    public static class BrandItem {
        @JsonProperty("brand_name")
        private String brandName;

        @JsonProperty("brand_icon")
        private String brandIcon;

        @JsonProperty("available_spareparts")
        private List<SparePartItem> availableSpareparts;

        public BrandItem(String brandName, String brandIcon, List<SparePartItem> availableSpareparts) {
            this.brandName = brandName;
            this.brandIcon = brandIcon;
            this.availableSpareparts = availableSpareparts;
        }

        public String getBrandName() { return brandName; }
        public List<SparePartItem> getAvailableSpareparts() { return availableSpareparts; }
    }

    public static class SparePartItem {
        @JsonProperty("spareparts_id")
        private Long sparepartsId;

        @JsonProperty("spareparts_type")
        private String sparepartsType;

        public SparePartItem(Long sparepartsId, String sparepartsType) {
            this.sparepartsId = sparepartsId;
            this.sparepartsType = sparepartsType;
        }

        public Long getSparepartsId() { return sparepartsId; }
        public String getSparepartsType() { return sparepartsType; }
    }
}
