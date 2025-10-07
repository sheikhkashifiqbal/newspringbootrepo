// com/car/carservices/mapper/BranchMapper.java
package com.car.carservices.mapper;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    @Autowired
    private CompanyRepository companyRepository;

    public BranchDTO toDTO(Branch e) {
        if (e == null) return null;
        BranchDTO d = new BranchDTO();
        d.setBranchId(e.getBranchId());
        d.setCompanyId(e.getCompany() != null ? e.getCompany().getCompanyId() : null);
        d.setBranchName(e.getBranchName());
        d.setBranchCode(e.getBranchCode());
        d.setBranchManagerName(e.getBranchManagerName());
        d.setBranchManagerSurname(e.getBranchManagerSurname());
        d.setBranchAddress(e.getBranchAddress());
        d.setLocation(e.getLocation());
        d.setLoginEmail(e.getLoginEmail());
        d.setPassword(e.getPassword());
        d.setLogoImg(e.getLogoImg());
        d.setBranchCoverImg(e.getBranchCoverImg());
        d.setStatus(e.getStatus());
        d.setLatitude(e.getLatitude());
        d.setLongitude(e.getLongitude());

        // NEW FIELDS
        d.setCity(e.getCity());
        d.setAddress(e.getAddress());
        return d;
    }

    public Branch toEntity(BranchDTO d) {
        if (d == null) return null;
        Branch e = new Branch();
        e.setBranchId(d.getBranchId());
        if (d.getCompanyId() != null) {
            e.setCompany(companyRepository.getReferenceById(d.getCompanyId()));
        }
        e.setBranchName(d.getBranchName());
        e.setBranchCode(d.getBranchCode());
        e.setBranchManagerName(d.getBranchManagerName());
        e.setBranchManagerSurname(d.getBranchManagerSurname());
        e.setBranchAddress(d.getBranchAddress());
        e.setLocation(d.getLocation());
        e.setLoginEmail(d.getLoginEmail());
        e.setPassword(d.getPassword());
        e.setLogoImg(d.getLogoImg());
        e.setBranchCoverImg(d.getBranchCoverImg());
        e.setStatus(d.getStatus());
        e.setLatitude(d.getLatitude());
        e.setLongitude(d.getLongitude());

        // NEW FIELDS
        e.setCity(d.getCity());
        e.setAddress(d.getAddress());
        return e;
    }
}
