package com.car.carservices.service.impl;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.repository.UserRegistrationRepository;
import com.car.carservices.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRegistrationRepository repository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        UserRegistration user = repository.findByEmailAndPassword(
                request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new LoginResponseDTO(user.getId(), user.getFullName(), user.getEmail(), "Login successful");
    }
}