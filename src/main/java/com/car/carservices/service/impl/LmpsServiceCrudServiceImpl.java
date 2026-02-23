package com.car.carservices.service.impl;

import com.car.carservices.dto.LmpsServiceRequestDTO;
import com.car.carservices.dto.LmpsServiceResponseDTO;
import com.car.carservices.entity.City;
import com.car.carservices.entity.LmpsService;
import com.car.carservices.entity.ServiceEntity;
import com.car.carservices.exception.LmpsServiceDuplicateException;
import com.car.carservices.repository.CityRepository;
import com.car.carservices.repository.LmpsServiceRepository;
import com.car.carservices.repository.ServiceRepository;
import com.car.carservices.service.LmpsServiceCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LmpsServiceCrudServiceImpl implements LmpsServiceCrudService {

    private final LmpsServiceRepository repo;
    private final ServiceRepository serviceRepository;
    private final CityRepository cityRepository;

    public LmpsServiceCrudServiceImpl(
            LmpsServiceRepository repo,
            ServiceRepository serviceRepository,
            CityRepository cityRepository
    ) {
        this.repo = repo;
        this.serviceRepository = serviceRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public LmpsServiceResponseDTO create(LmpsServiceRequestDTO dto) {
        if (dto.getServiceId() == null) throw new IllegalArgumentException("service_id is required");
        if (dto.getCityId() == null) throw new IllegalArgumentException("city_id is required");

        // ✅ UNIQUE CHECK (service_id + city_id)
        if (repo.existsByService_ServiceIdAndCity_Id(dto.getServiceId(), dto.getCityId())) {
            throw new LmpsServiceDuplicateException("Duplicate values are not allowed");
        }

        ServiceEntity serviceEntity = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + dto.getServiceId()));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + dto.getCityId()));

        LmpsService e = new LmpsService();
        e.setService(serviceEntity);
        e.setCity(city);
        e.setPrice(dto.getPrice());
        e.setStatus(dto.getStatus());
        e.setCurrency(dto.getCurrency());

        e = repo.save(e);
        return toResponse(e);
    }

    @Override
    public LmpsServiceResponseDTO getById(Long lmpsServiceId) {
        LmpsService e = repo.findById(lmpsServiceId)
                .orElseThrow(() -> new IllegalArgumentException("lmps_service not found: " + lmpsServiceId));
        return toResponse(e);
    }

    @Override
    public List<LmpsServiceResponseDTO> getAll(Long serviceId, Long cityId) {
        List<LmpsService> list;

        if (serviceId != null && cityId != null) {
            list = repo.findByService_ServiceIdAndCity_Id(serviceId, cityId);
        } else if (serviceId != null) {
            list = repo.findByService_ServiceId(serviceId);
        } else if (cityId != null) {
            list = repo.findByCity_Id(cityId);
        } else {
            list = repo.findAll();
        }

        return list.stream().map(this::toResponse).toList();
    }

    @Override
    public LmpsServiceResponseDTO update(Long lmpsServiceId, LmpsServiceRequestDTO dto) {
        LmpsService e = repo.findById(lmpsServiceId)
                .orElseThrow(() -> new IllegalArgumentException("lmps_service not found: " + lmpsServiceId));

        // Determine target serviceId/cityId after update
        Long targetServiceId = dto.getServiceId() != null ? dto.getServiceId() : (e.getService() != null ? e.getService().getServiceId() : null);
        Long targetCityId = dto.getCityId() != null ? dto.getCityId() : (e.getCity() != null ? e.getCity().getId() : null);

        if (targetServiceId == null) throw new IllegalArgumentException("service_id is required");
        if (targetCityId == null) throw new IllegalArgumentException("city_id is required");

        // ✅ UNIQUE CHECK excluding current row
        if (repo.existsByService_ServiceIdAndCity_IdAndLmpsServiceIdNot(targetServiceId, targetCityId, lmpsServiceId)) {
            throw new LmpsServiceDuplicateException("Duplicate values are not allowed");
        }

        if (dto.getServiceId() != null) {
            ServiceEntity serviceEntity = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Service not found: " + dto.getServiceId()));
            e.setService(serviceEntity);
        }

        if (dto.getCityId() != null) {
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new IllegalArgumentException("City not found: " + dto.getCityId()));
            e.setCity(city);
        }

        if (dto.getPrice() != null) e.setPrice(dto.getPrice());
        if (dto.getStatus() != null) e.setStatus(dto.getStatus());
        if (dto.getCurrency() != null) e.setCurrency(dto.getCurrency());

        e = repo.save(e);
        return toResponse(e);
    }

    @Override
    public void delete(Long lmpsServiceId) {
        if (!repo.existsById(lmpsServiceId)) {
            throw new IllegalArgumentException("lmps_service not found: " + lmpsServiceId);
        }
        repo.deleteById(lmpsServiceId);
    }

    private LmpsServiceResponseDTO toResponse(LmpsService e) {
        Long serviceId = e.getService() != null ? e.getService().getServiceId() : null;
        Long cityId = e.getCity() != null ? e.getCity().getId() : null;

        return new LmpsServiceResponseDTO(
                e.getLmpsServiceId(),
                serviceId,
                cityId,
                e.getPrice(),
                e.getStatus(),
                e.getCurrency()
        );
    }
}
