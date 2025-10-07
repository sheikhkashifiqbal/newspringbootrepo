// com/car/carservices/repository/ServiceEntityRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByServiceNameIgnoreCase(String name);
}
