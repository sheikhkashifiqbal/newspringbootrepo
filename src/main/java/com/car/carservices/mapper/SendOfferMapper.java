package com.car.carservices.mapper;

import com.car.carservices.dto.SendOfferDTO;
import com.car.carservices.entity.SendOffer;
import org.springframework.stereotype.Component;

@Component
public class SendOfferMapper {
    public SendOfferDTO toDTO(SendOffer entity) {
        SendOfferDTO dto = new SendOfferDTO();
        dto.setSendofferId(entity.getSendofferId());
        dto.setSparepartsrequestId(entity.getSparepartsrequestId());
        dto.setOfferClass(entity.getOfferClass());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public SendOffer toEntity(SendOfferDTO dto) {
        SendOffer entity = new SendOffer();
        entity.setSendofferId(dto.getSendofferId());
        entity.setSparepartsrequestId(dto.getSparepartsrequestId());
        entity.setOfferClass(dto.getOfferClass());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
