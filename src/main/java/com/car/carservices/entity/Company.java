package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "company",
       uniqueConstraints = {
         @UniqueConstraint(name = "ux_company_manager_email_ci", columnNames = {"managerEmail"}),
         @UniqueConstraint(name = "ux_company_company_name_ci", columnNames = {"companyName"}),
         @UniqueConstraint(name = "ux_company_brand_name_ci", columnNames = {"brandName"})
       })
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String brandName;

    private String taxId;
    private String managerName;
    private String managerSurname;
    private String managerPhone;
    private String managerMobile;

    @Column(nullable = false)
    private String managerEmail;

    private String website;
    private String password;
    private String tinPhoto;
}
