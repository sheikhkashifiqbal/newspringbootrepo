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
    @Autowired private UserRegistrationRepository repository;
    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    // NEW: public helper for “check” endpoints
    public boolean isEmailUnique(String email) {
        return !repository.existsByEmailIgnoreCase(email);
    }

    public UserRegistrationDTO save(UserRegistrationDTO dto) {
        // NEW: uniqueness guard on create
        if (!isEmailUnique(dto.getEmail())) {
            throw new IllegalArgumentException("Duplicate email");
        }
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

        // NEW: uniqueness guard on update (exclude current user)
        if (dto.getEmail() != null &&
            repository.existsByEmailIgnoreCaseAndIdNot(dto.getEmail(), id)) {
            throw new IllegalArgumentException("Duplicate email");
        }

        existing.setFullName(dto.getFullName());
        existing.setBirthday(dto.getBirthday());
        existing.setGender(dto.getGender());
        existing.setEmail(dto.getEmail());

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
