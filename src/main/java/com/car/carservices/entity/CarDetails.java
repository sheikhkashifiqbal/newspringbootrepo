package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserRegistration user;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    private String carModel;
    private String vinNumber;
    private String plateNumber;
}

