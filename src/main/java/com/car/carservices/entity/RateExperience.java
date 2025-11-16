package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "rate_experience")
public class RateExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_experienceid")
    private Long rateExperienceID;

    // ✅ Correct column only
    @Column(name = "branch_brand_serviceid")
    private Long branchBrandServiceID;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Column(name = "stars")
    private int stars;

    @Column(name = "date")
    private LocalDate date;
}
