package com.car.carservices.service.impl;

import com.car.carservices.dto.StrpStripeCompanySubsRequestDTO;
import com.car.carservices.dto.StrpStripeCompanySubsResponseDTO;
import com.car.carservices.entity.Company;
import com.car.carservices.entity.StrpStripeCompanySubs;
import com.car.carservices.repository.StrpStripeCompanySubsRepository;
import com.car.carservices.service.StrpStripeCompanySubsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StrpStripeCompanySubsServiceImpl implements StrpStripeCompanySubsService {

    private final StrpStripeCompanySubsRepository repo;
    private final com.car.carservices.repository.CompanyRepository companyRepository;

    public StrpStripeCompanySubsServiceImpl(
            StrpStripeCompanySubsRepository repo,
            com.car.carservices.repository.CompanyRepository companyRepository
    ) {
        this.repo = repo;
        this.companyRepository = companyRepository;
    }

    @Override
    public StrpStripeCompanySubsResponseDTO create(StrpStripeCompanySubsRequestDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + dto.getCompanyId()));

        StrpStripeCompanySubs e = new StrpStripeCompanySubs();
        e.setCompany(company);
        e.setStripeCustomerId(dto.getStripeCustomerId());
        e.setStripeSubscriptionId(dto.getStripeSubscriptionId());
        e.setPaymentMethodId(dto.getPaymentMethodId());
        e.setStatus(dto.getStatus());

        // preserve provided createdAt; otherwise set now
        e.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        e = repo.save(e);
        return toResponse(e);
    }

    @Override
    public StrpStripeCompanySubsResponseDTO getById(Long id) {
        StrpStripeCompanySubs e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stripe subscription not found: " + id));
        return toResponse(e);
    }

    @Override
    public List<StrpStripeCompanySubsResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<StrpStripeCompanySubsResponseDTO> getByCompanyId(Long companyId) {
        return repo.findByCompany_CompanyId(companyId).stream().map(this::toResponse).toList();
    }

    @Override
    public StrpStripeCompanySubsResponseDTO update(Long id, StrpStripeCompanySubsRequestDTO dto) {
        StrpStripeCompanySubs e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stripe subscription not found: " + id));

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Company not found: " + dto.getCompanyId()));
            e.setCompany(company);
        }

        // Update only when provided (keeps existing functionality safe)
        if (dto.getStripeCustomerId() != null) e.setStripeCustomerId(dto.getStripeCustomerId());
        if (dto.getStripeSubscriptionId() != null) e.setStripeSubscriptionId(dto.getStripeSubscriptionId());
        if (dto.getPaymentMethodId() != null) e.setPaymentMethodId(dto.getPaymentMethodId());
        if (dto.getStatus() != null) e.setStatus(dto.getStatus());
        if (dto.getCreatedAt() != null) e.setCreatedAt(dto.getCreatedAt());

        e = repo.save(e);
        return toResponse(e);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Stripe subscription not found: " + id);
        }
        repo.deleteById(id);
    }

    private StrpStripeCompanySubsResponseDTO toResponse(StrpStripeCompanySubs e) {
        Long companyId = e.getCompany() != null ? e.getCompany().getCompanyId() : null;
        return new StrpStripeCompanySubsResponseDTO(
                e.getId(),
                companyId,
                e.getStripeCustomerId(),
                e.getStripeSubscriptionId(),
                e.getPaymentMethodId(),
                e.getStatus(),
                e.getCreatedAt()
        );
    }
}
