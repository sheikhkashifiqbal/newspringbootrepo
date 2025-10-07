package com.car.carservices.service;

import com.car.carservices.dto.UserRegistrationDTO;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.mapper.UserMapper;
import com.car.carservices.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRegistrationRepository repository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRegistrationDTO save(UserRegistrationDTO dto) {
        UserRegistration entity = userMapper.toEntity(dto);
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        UserRegistration saved = repository.save(entity);
        return userMapper.toDTO(saved);
    }

    public List<UserRegistrationDTO> getAll() {
        return repository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<UserRegistrationDTO> getById(Long id) {
        return repository.findById(id).map(userMapper::toDTO);
    }

    public UserRegistrationDTO update(Long id, UserRegistrationDTO dto) {
        UserRegistration existing = repository.findById(id).orElseThrow();
        // Map all updatable fields
        existing.setFullName(dto.getFullName());
        existing.setBirthday(dto.getBirthday());
        existing.setGender(dto.getGender());
        existing.setEmail(dto.getEmail());

        // Only update password if caller provided one; encode it
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        UserRegistration saved = repository.save(existing);
        return userMapper.toDTO(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
