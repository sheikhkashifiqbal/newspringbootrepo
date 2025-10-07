// src/main/java/com/car/carservices/mapper/SparePartsRequestDetailMapper.java
package com.car.carservices.mapper;

import com.car.carservices.dto.SparePartsRequestDetailDTO;
import com.car.carservices.entity.SparePartsRequestDetail;
import org.springframework.stereotype.Component;

@Component
public class SparePartsRequestDetailMapper {

    public SparePartsRequestDetailDTO toDTO(SparePartsRequestDetail e) {
        if (e == null) return null;
        SparePartsRequestDetailDTO d = new SparePartsRequestDetailDTO();
        d.setId(e.getId());
        d.setSparepartsrequestId(e.getSparepartsrequestId());
        d.setSparePart(e.getSparePart());
        d.setClassType(e.getClassType());
        d.setQty(e.getQty());
        d.setPrice(e.getPrice());
        return d;
    }

    public SparePartsRequestDetail toEntity(SparePartsRequestDetailDTO d) {
        if (d == null) return null;
        SparePartsRequestDetail e = new SparePartsRequestDetail();
        e.setId(d.getId());
        e.setSparepartsrequestId(d.getSparepartsrequestId());
        e.setSparePart(d.getSparePart());
        e.setClassType(d.getClassType());
        e.setQty(d.getQty());
        e.setPrice(d.getPrice());
        return e;
    }
}
