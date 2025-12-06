package com.car.carservices.repository;

import com.car.carservices.entity.DisableTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisableTimeSlotRepository extends JpaRepository<DisableTimeSlot, Long> {

}
