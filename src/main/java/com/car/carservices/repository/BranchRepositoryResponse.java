package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import com.car.carservices.dto.BranchSearchResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BranchRepositoryResponse extends JpaRepository<Branch, Long> {

    @Query("""
        SELECT new com.car.carservices.dto.BranchSearchResponseDTO(
            c.companyName, b.branchName, s.serviceName, br.brandName, b.location
        )
        FROM Branch b
        JOIN b.company c
        JOIN BranchBrandService bbs ON bbs.branch.id = b.branchId
        JOIN Brand br ON br.brandId = bbs.brand.id
        JOIN ServiceEntity s ON s.serviceId = bbs.service.id
        WHERE (:brandName IS NULL OR LOWER(br.brandName) = LOWER(:brandName))
          AND (:serviceName IS NULL OR LOWER(s.serviceName) = LOWER(:serviceName))
        
          AND (:location IS NULL OR LOWER(b.location) LIKE LOWER(CONCAT('%', :location, '%')))
    """)
    List<BranchSearchResponseDTO> searchBranches(
        @Param("brandName") String brandName,
        @Param("serviceName") String serviceName,
       // @Param("workdays") String workdays,
        @Param("location") String location
    );
}
