package com.car.carservices.mapper;

import com.car.carservices.dto.UserRegistrationDTO;
import com.car.carservices.entity.UserRegistration;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRegistrationDTO toDTO(UserRegistration user) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setBirthday(user.getBirthday());
        dto.setGender(user.getGender());
        dto.setEmail(user.getEmail());
        // Do not expose password (hashed or otherwise)
        dto.setPassword(null);
        return dto;
    }

    public UserRegistration toEntity(UserRegistrationDTO dto) {
        UserRegistration user = new UserRegistration();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setBirthday(dto.getBirthday());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        // Raw password will be encoded in the Service layer
        user.setPassword(dto.getPassword());
        return user;
    }
}
