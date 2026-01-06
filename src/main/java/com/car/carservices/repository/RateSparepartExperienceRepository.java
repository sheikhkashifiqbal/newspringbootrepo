package com.car.carservices.repository;


import com.car.carservices.entity.RateSparepartExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface RateSparepartExperienceRepository
extends JpaRepository<RateSparepartExperienceEntity, Long> {


List<RateSparepartExperienceEntity> findByBranchBrandSparepartId(Long branchBrandSparepartId);
}