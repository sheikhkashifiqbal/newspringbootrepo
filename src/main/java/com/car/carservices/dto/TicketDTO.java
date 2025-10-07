package com.car.carservices.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private Long ticketId;
    private Long problemId;
    private String description;
    private String attachImg;
}