package com.car.carservices.repository;

import com.car.carservices.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findByEmailAndPassword(String email, String password); // legacy; avoid for login
    Optional<UserRegistration> findByEmail(String email);

    // NEW: case-insensitive uniqueness checks
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
     boolean existsByMobileIgnoreCase(String mobile);

    Optional<UserRegistration> findFirstByEmailIgnoreCase(String email);
}