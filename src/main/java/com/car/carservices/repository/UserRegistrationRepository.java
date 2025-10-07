package com.car.carservices.repository;

import com.car.carservices.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findByEmailAndPassword(String email, String password);
    Optional<UserRegistration> findByEmail(String email);
}