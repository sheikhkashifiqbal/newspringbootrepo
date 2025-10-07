package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SendOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sendofferId;

    private Long sparepartsrequestId;
    private String offerClass;
    private Double price;
}
