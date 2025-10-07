package com.car.carservices.service;

import com.car.carservices.dto.ReservationServiceSparepartDTO;
import com.car.carservices.entity.ReservationServiceSparepart;
import com.car.carservices.mapper.ReservationServiceSparepartMapper;
import com.car.carservices.repository.ReservationServiceSparepartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.car.carservices.mapper.ReservationServiceSparepartMapper.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceSparepartService {

    private final ReservationServiceSparepartRepository repository;

    public ReservationServiceSparepartDTO create(ReservationServiceSparepartDTO dto) {
        ReservationServiceSparepart saved = repository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public ReservationServiceSparepartDTO get(Long id) {
        ReservationServiceSparepart e = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("reservation_service_sparepart not found: " + id));
        return toDTO(e);
    }

    @Transactional(readOnly = true)
    public List<ReservationServiceSparepartDTO> listByReservation(Long reservationId) {
        return repository.findByReservationId(reservationId)
                .stream().map(ReservationServiceSparepartMapper::toDTO).toList();
    }

    public ReservationServiceSparepartDTO update(Long id, ReservationServiceSparepartDTO dto) {
        ReservationServiceSparepart e = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("reservation_service_sparepart not found: " + id));
        merge(e, dto);
        repository.save(e);
        return toDTO(e);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("reservation_service_sparepart not found: " + id);
        }
        repository.deleteById(id);
    }
}
