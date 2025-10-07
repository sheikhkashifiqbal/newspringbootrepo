package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String serviceName;

   // @ManyToOne
   // @JoinColumn(name = "brand_model_id")
   // private CarBrandModel carBrandModel;
}