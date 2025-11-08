package com.car.carservices.dto;

import java.util.List;

public class PRSparePartBranchItem {
    private Long branch_id;
    private String branch_name;
    private String logo_img;
    private List<String> state;       // final available subset for this branch
    private String spareparts_type;   // from spare_parts
    private Double latitude;
    private Double longitude;
    private Integer stars;            // NEW: mode of stars (nullable)

    public PRSparePartBranchItem(Long branch_id,
                                 String branch_name,
                                 String logo_img,
                                 List<String> state,
                                 String spareparts_type,
                                 Double latitude,
                                 Double longitude,
                                 Integer stars) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.logo_img = logo_img;
        this.state = state;
        this.spareparts_type = spareparts_type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
    }

    public Long getBranch_id() { return branch_id; }
    public String getBranch_name() { return branch_name; }
    public String getLogo_img() { return logo_img; }
    public List<String> getState() { return state; }
    public String getSpareparts_type() { return spareparts_type; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getStars() { return stars; }
}
