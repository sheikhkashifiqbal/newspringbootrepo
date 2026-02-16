package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class SparepartsRatingResponse {

    private List<SparepartsRatingRowDto> ratings = new ArrayList<>();

    @JsonProperty("star_1")
    private String star1;

    @JsonProperty("star_2")
    private String star2;

    @JsonProperty("star_3")
    private String star3;

    @JsonProperty("star_4")
    private String star4;

    @JsonProperty("star_5")
    private String star5;

    @JsonProperty("total_ratings")
    private long totalRatings;

    public List<SparepartsRatingRowDto> getRatings() {
        return ratings;
    }
    public void setRatings(List<SparepartsRatingRowDto> ratings) {
        this.ratings = ratings;
    }

    public String getStar1() {
        return star1;
    }
    public void setStar1(String star1) {
        this.star1 = star1;
    }

    public String getStar2() {
        return star2;
    }
    public void setStar2(String star2) {
        this.star2 = star2;
    }

    public String getStar3() {
        return star3;
    }
    public void setStar3(String star3) {
        this.star3 = star3;
    }

    public String getStar4() {
        return star4;
    }
    public void setStar4(String star4) {
        this.star4 = star4;
    }

    public String getStar5() {
        return star5;
    }
    public void setStar5(String star5) {
        this.star5 = star5;
    }

    public long getTotalRatings() {
        return totalRatings;
    }
    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }
}
