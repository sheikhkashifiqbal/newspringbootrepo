package com.car.carservices.controller;

import com.car.carservices.dto.PasswordResetRequestDTO;
import com.car.carservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }
}
