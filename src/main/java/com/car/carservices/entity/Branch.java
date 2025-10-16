// com/car/carservices/entity/Branch.java
package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String branchName;
    private String branchCode;
    private String branchManagerName;
    private String branchManagerSurname;
    private String branchAddress;
    private String location;
    private String loginEmail;
    private String password;
    private String logoImg;
    private String branchCoverImg;
    private String status = "disapproved";

    @Column(columnDefinition = "double precision")
    private Double latitude;

    @Column(columnDefinition = "double precision")
    private Double longitude;

    // NEW FIELDS (additive)
    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")   // must match the DB column name
    private String mobile;     // <-- NEW property
}
