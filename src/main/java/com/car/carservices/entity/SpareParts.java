package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SpareParts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sparepartsId;

    private String sparepartsType;
    private String condition;
}