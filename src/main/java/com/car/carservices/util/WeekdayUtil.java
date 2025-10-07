// com/car/carservices/util/WeekdayUtil.java
package com.car.carservices.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public final class WeekdayUtil {
    private WeekdayUtil(){}
    public static String weekday(LocalDate d) {
        if (d == null) return null;
        return d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase(); // "monday"
    }
}
