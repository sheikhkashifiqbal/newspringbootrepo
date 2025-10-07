package com.car.carservices.service;

import com.car.carservices.dto.LoginRequestDTO;
import com.car.carservices.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}