package com.car.carservices.dto;

import lombok.Data;

@Data
public class SendOfferDTO {
    private Long sendofferId;
    private Long sparepartsrequestId;
    private String offerClass;
    private Double price;
}
