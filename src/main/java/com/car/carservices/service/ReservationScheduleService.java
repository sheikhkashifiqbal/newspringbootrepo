package com.car.carservices.service;

import com.car.carservices.dto.BranchDayReservationItem;
import com.car.carservices.dto.BranchDayScheduleRequest;
import com.car.carservices.dto.BranchDayScheduleResponse;
import com.car.carservices.repository.ReservationScheduleDao;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class ReservationScheduleService {

    private final ReservationScheduleDao dao;

    public ReservationScheduleService(ReservationScheduleDao dao) {
        this.dao = dao;
    }

    public List<BranchDayScheduleResponse> getBranchDaySchedule(BranchDayScheduleRequest request) {
        if (request.getBranchId() == null || request.getReservationDate() == null) {
            return Collections.emptyList();
        }

        LocalDate date;
        try {
            date = LocalDate.parse(request.getReservationDate()); // "2025-11-27"
        } catch (DateTimeParseException e) {
            // invalid date format
            return Collections.emptyList();
        }

        // Derive working_day name from date, e.g. "Thursday"
        DayOfWeek dow = date.getDayOfWeek();
        String workingDay = dow.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
        // Ensure first letter capital, rest lower (e.g. "Thursday")
        workingDay = workingDay.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + workingDay.substring(1).toLowerCase(Locale.ENGLISH);

        // Fetch work day row for from/to
        ReservationScheduleDao.WorkDayRow wd =
                dao.findWorkDay(request.getBranchId(), workingDay);

        String from = wd != null ? wd.getFrom() : null;
        String to   = wd != null ? wd.getTo()   : null;

        // Fetch reservations
        List<BranchDayReservationItem> reservations =
                dao.findReservationsForBranchAndDate(request.getBranchId(), date);

        BranchDayScheduleResponse response =
                new BranchDayScheduleResponse(workingDay, from, to, reservations);

        // Spec shows JSON Response as array with single object
        return List.of(response);
    }
}
