package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rate_sparepart_experience")
@Getter
@Setter
public class RateSparepartExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_experience_id")
    private Long rateExperienceId;

    @Column(name = "branch_brand_sparepart_id")
    private Long branchBrandSparepartId;

    @Column(name = "description")
    private String description;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "user_id")
    private Long userId;

    /**
     * Keeping it as String because your example is "14.01.2026".
     * If your DB column is DATE/TIMESTAMP, change this to LocalDate/LocalDateTime.
     */
    @Column(name = "date")
    private String date;

    @Column(name = "sparepartsrequest_id")
    private Long sparepartsrequestId;
}
