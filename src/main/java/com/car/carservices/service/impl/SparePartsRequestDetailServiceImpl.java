// src/main/java/com/car/carservices/service/impl/SparePartsRequestDetailServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsRequestDetailDTO;
import com.car.carservices.entity.SparePartsRequestDetail;
import com.car.carservices.mapper.SparePartsRequestDetailMapper;
import com.car.carservices.repository.SparePartsRequestDetailRepository;
import com.car.carservices.service.SparePartsRequestDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class SparePartsRequestDetailServiceImpl implements SparePartsRequestDetailService {

    private final SparePartsRequestDetailRepository repo;
    private final SparePartsRequestDetailMapper mapper;

    public SparePartsRequestDetailServiceImpl(SparePartsRequestDetailRepository repo,
                                              SparePartsRequestDetailMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    // ✅ helper: treat missing values as empty string
    private String emptyIfNull(String v) {
        return v == null ? "" : v.trim();
    }

    @Override
    public SparePartsRequestDetailDTO create(SparePartsRequestDetailDTO dto) {
        // ✅ Apply defaults if not sent in JSON
        dto.setCurrency(emptyIfNull(dto.getCurrency()));
        dto.setPaymentStatus(emptyIfNull(dto.getPaymentStatus()));

        SparePartsRequestDetail entity = mapper.toEntity(dto);

        // ✅ Safety: ensure entity also has defaults (in case mapper bypasses dto setters)
        entity.setCurrency(emptyIfNull(entity.getCurrency()));
        entity.setPaymentStatus(emptyIfNull(entity.getPaymentStatus()));

        return mapper.toDTO(repo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SparePartsRequestDetailDTO get(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SparePartsRequestDetailDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SparePartsRequestDetailDTO> getByRequestId(Long sparepartsrequestId) {
        return repo.findBySparepartsrequestId(sparepartsrequestId).stream().map(mapper::toDTO).toList();
    }

    @Override
    public SparePartsRequestDetailDTO update(Long id, SparePartsRequestDetailDTO dto) {
        SparePartsRequestDetail e = repo.findById(id).orElseThrow();

        e.setSparepartsrequestId(dto.getSparepartsrequestId());
        e.setSparePart(dto.getSparePart());
        e.setClassType(dto.getClassType());
        e.setQty(dto.getQty());
        e.setPrice(dto.getPrice());

        // ✅ Apply defaults if not sent in JSON
        e.setCurrency(emptyIfNull(dto.getCurrency()));
        e.setPaymentStatus(emptyIfNull(dto.getPaymentStatus()));

        return mapper.toDTO(repo.save(e));
    }

    // ✅ NEW: bulk update payment_status for all details rows under one sparepartsrequest_id
    @Override
    public int updatePaymentStatusByRequestId(Long sparepartsrequestId, String paymentStatus) {
        if (sparepartsrequestId == null) {
            throw new IllegalArgumentException("sparepartsrequest_id is required");
        }
        if (paymentStatus == null || paymentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("payment_status is required");
        }

        String status = paymentStatus.trim().toLowerCase(Locale.ROOT);
        if (!status.equals("paid") && !status.equals("unpaid")) {
            throw new IllegalArgumentException("payment_status must be 'paid' or 'unpaid'");
        }

        return repo.updatePaymentStatusByRequestId(sparepartsrequestId, status);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public long deleteByRequestId(Long sparepartsrequestId) {
        return repo.deleteBySparepartsrequestId(sparepartsrequestId);
    }
}