package com.car.carservices.mapper;

import com.car.carservices.dto.ServiceEntityDTO;
//import com.car.carservices.entity.CarBrandModel;
import com.car.carservices.entity.ServiceEntity;
//import com.car.carservices.repository.CarBrandModelRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceEntityMapper {

   // @Autowired
    //private CarBrandModelRepository brandModelRepo;

    public ServiceEntity toEntity(ServiceEntityDTO dto) {
        ServiceEntity entity = new ServiceEntity();
        entity.setServiceId(dto.getServiceId());
        entity.setServiceName(dto.getServiceName());
        entity.setServiceType(dto.getServiceType());
       // entity.setCarBrandModel(brandModelRepo.findById(dto.getBrandModelId()).orElse(null));
        return entity;
    }

    public ServiceEntityDTO toDTO(ServiceEntity entity) {
        ServiceEntityDTO dto = new ServiceEntityDTO();
        dto.setServiceId(entity.getServiceId());
        dto.setServiceName(entity.getServiceName());
        dto.setServiceType(entity.getServiceType());
        //dto.setBrandModelId(entity.getCarBrandModel().getId());
        return dto;
    }
}