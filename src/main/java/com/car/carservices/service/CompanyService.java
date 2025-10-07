package com.car.carservices.service;

import com.car.carservices.dto.CompanyDTO;
import com.car.carservices.entity.Company;
import com.car.carservices.mapper.CompanyMapper;
import com.car.carservices.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired private CompanyRepository repo;
    @Autowired private CompanyMapper mapper;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<CompanyDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public CompanyDTO get(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    // === public helpers for "check" APIs ===
    public boolean isManagerEmailUnique(String email) {
        return !repo.existsByManagerEmailIgnoreCase(email);
    }
    public boolean isCompanyNameUnique(String name) {
        return !repo.existsByCompanyNameIgnoreCase(name);
    }
    public boolean isBrandNameUnique(String name) {
        return !repo.existsByBrandNameIgnoreCase(name);
    }

    public CompanyDTO save(CompanyDTO dto) {
        if (dto.getCompanyId() == null) {
            // CREATE: all must be unique
            if (repo.existsByManagerEmailIgnoreCase(dto.getManagerEmail())
             || repo.existsByCompanyNameIgnoreCase(dto.getCompanyName())
             || repo.existsByBrandNameIgnoreCase(dto.getBrandName())) {
                throw new IllegalArgumentException("Duplicate names are not allowed");
            }

            Company toSave = mapper.toEntity(dto);
            if (toSave.getPassword() != null && !toSave.getPassword().isBlank()) {
                toSave.setPassword(passwordEncoder.encode(toSave.getPassword()));
            }
            return mapper.toDTO(repo.save(toSave));

        } else {
            // UPDATE: must be unique except for this row
            Long id = dto.getCompanyId();
            if (repo.existsByManagerEmailIgnoreCaseAndCompanyIdNot(dto.getManagerEmail(), id)
             || repo.existsByCompanyNameIgnoreCaseAndCompanyIdNot(dto.getCompanyName(), id)
             || repo.existsByBrandNameIgnoreCaseAndCompanyIdNot(dto.getBrandName(), id)) {
                throw new IllegalArgumentException("Duplicate names are not allowed");
            }

            Company existing = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));

            // copy fields (except password)
            existing.setCompanyName(dto.getCompanyName());
            existing.setBrandName(dto.getBrandName());
            existing.setTaxId(dto.getTaxId());
            existing.setManagerName(dto.getManagerName());
            existing.setManagerSurname(dto.getManagerSurname());
            existing.setManagerPhone(dto.getManagerPhone());
            existing.setManagerMobile(dto.getManagerMobile());
            existing.setManagerEmail(dto.getManagerEmail());
            existing.setWebsite(dto.getWebsite());
            existing.setTinPhoto(dto.getTinPhoto());

            // update password only if a new raw one is sent
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                String raw = dto.getPassword();
                if (!isLikelyHash(raw)) {
                    existing.setPassword(passwordEncoder.encode(raw));
                }
            }

            return mapper.toDTO(repo.save(existing));
        }
    }

    public void delete(Long id) { repo.deleteById(id); }

    private boolean isLikelyHash(String v) {
        return v != null && (v.startsWith("$2a$") || v.startsWith("$2b$") || v.startsWith("$2y$")
                || v.startsWith("$argon2"));
    }
}
