package com.car.carservices.service;

import com.car.carservices.dto.PasswordResetRequestDTO;

public interface UserService {
    void resetPassword(PasswordResetRequestDTO dto);
}
