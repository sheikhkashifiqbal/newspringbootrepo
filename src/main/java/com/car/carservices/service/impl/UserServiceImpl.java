package com.car.carservices.service.impl;

import com.car.carservices.dto.PasswordResetRequestDTO;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.repository.UserRegistrationRepository;
import com.car.carservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRegistrationRepository repository;

    @Override
    public void resetPassword(PasswordResetRequestDTO dto) {
        UserRegistration user = repository.findByEmailAndPassword(dto.getEmail(), dto.getOldPassword())
                .orElseThrow(() -> new RuntimeException("Invalid email or old password"));

        user.setPassword(dto.getNewPassword());
        repository.save(user);
    }
}
