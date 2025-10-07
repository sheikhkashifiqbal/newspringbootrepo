package com.car.carservices.mapper;

import com.car.carservices.dto.WorkDayDTO;
import com.car.carservices.entity.WorkDay;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class WorkDayMapper {

    public static WorkDayDTO toDTO(WorkDay e) {
        WorkDayDTO d = new WorkDayDTO();
        d.setWorkId(e.getWorkId());
        // branch is a relation; extract its id safely
        d.setBranchId(e.getBranch() != null ? e.getBranch().getBranchId() : null);
        d.setWorkingDay(e.getWorkingDay());
        d.setFrom(formatTimeOrNull(e.getFrom())); // field name is 'from'
        d.setTo(formatTimeOrNull(e.getTo()));     // field name is 'to'
        d.setStatus(e.getStatus());
        return d;
    }

    public static WorkDay toEntity(WorkDayDTO d) {
        WorkDay e = new WorkDay();
        e.setWorkId(d.getWorkId());
        // branch will be set in Service via EntityManager (to avoid touching your entity here)
        if (d.getWorkingDay() != null) e.setWorkingDay(d.getWorkingDay());
        if (d.getFrom() != null) e.setFrom(parseTimeOrNull(d.getFrom()));
        if (d.getTo() != null) e.setTo(parseTimeOrNull(d.getTo()));
        if (d.getStatus() != null) e.setStatus(d.getStatus());
        return e;
    }

    /** Partial update: only apply non-null fields from DTO */
    public static void merge(WorkDay e, WorkDayDTO d) {
        // branch handled in Service
        if (d.getWorkingDay() != null) e.setWorkingDay(d.getWorkingDay());
        if (d.getFrom() != null) e.setFrom(parseTimeOrNull(d.getFrom()));
        if (d.getTo() != null) e.setTo(parseTimeOrNull(d.getTo()));
        if (d.getStatus() != null) e.setStatus(d.getStatus());
    }

    private static LocalTime parseTimeOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalTime.parse(s); // expects "HH:mm"
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid time format (use HH:mm): " + s);
        }
    }

    private static String formatTimeOrNull(LocalTime t) {
        return t == null ? null : t.toString(); // "HH:mm"
    }
}
