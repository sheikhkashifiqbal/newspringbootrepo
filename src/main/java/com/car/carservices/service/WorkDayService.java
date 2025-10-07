package com.car.carservices.service;

import com.car.carservices.dto.WorkDayDTO;
import com.car.carservices.entity.WorkDay;
import com.car.carservices.mapper.WorkDayMapper;
import com.car.carservices.repository.WorkDayRepository;
import com.car.carservices.entity.Branch; // <- existing entity in your project
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkDayService {

    private final WorkDayRepository repo;

    @PersistenceContext
    private EntityManager em;

    public WorkDayDTO create(WorkDayDTO dto) {
        WorkDay e = WorkDayMapper.toEntity(dto);
        if (dto.getBranchId() != null) {
            // attach branch without loading fully
            e.setBranch(em.getReference(Branch.class, dto.getBranchId()));
        }
        WorkDay saved = repo.save(e);
        return WorkDayMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public WorkDayDTO getById(Long workId) {
        WorkDay e = repo.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("work_days not found: " + workId));
        return WorkDayMapper.toDTO(e);
    }

    @Transactional(readOnly = true)
    public List<WorkDayDTO> listByBranchId(Long branchId) {
        // Note: WorkDay has 'branch' relation, not 'branchId' field
        List<WorkDay> rows = em.createQuery(
                "select w from WorkDay w where w.branch.branchId = :branchId", WorkDay.class)
                .setParameter("branchId", branchId)
                .getResultList();
        return rows.stream().map(WorkDayMapper::toDTO).toList();
    }

    /** Partial update: only fields provided in DTO are modified */
    public WorkDayDTO updatePartial(Long workId, WorkDayDTO dto) {
        WorkDay e = repo.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("work_days not found: " + workId));

        // Update branch only if provided
        if (dto.getBranchId() != null) {
            e.setBranch(em.getReference(Branch.class, dto.getBranchId()));
        }

        WorkDayMapper.merge(e, dto);
        repo.save(e);
        return WorkDayMapper.toDTO(e);
    }

    public void delete(Long workId) {
        if (!repo.existsById(workId)) {
            throw new EntityNotFoundException("work_days not found: " + workId);
        }
        repo.deleteById(workId);
    }
}
