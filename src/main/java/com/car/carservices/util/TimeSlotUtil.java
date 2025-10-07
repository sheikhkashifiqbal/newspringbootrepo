// com/car/carservices/util/TimeSlotUtil.java
package com.car.carservices.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class TimeSlotUtil {
    private TimeSlotUtil(){}
    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    public static List<String> slotsInclusive(LocalTime from, LocalTime to, int stepMinutes) {
        if (from == null || to == null) return List.of();
        List<String> out = new ArrayList<>();
        for (LocalTime t = from; !t.isAfter(to); t = t.plusMinutes(stepMinutes)) {
            out.add(t.format(HHMM));
        }
        return out;
    }
}
