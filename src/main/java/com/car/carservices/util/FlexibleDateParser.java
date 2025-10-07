// com/car/carservices/util/FlexibleDateParser.java
package com.car.carservices.util;

import java.time.LocalDate;
import java.time.format.*;
import java.util.Locale;

public final class FlexibleDateParser {
    private FlexibleDateParser(){}

    public static LocalDate parse(LocalDate isoDate, String dateText) {
        if (isoDate != null) return isoDate;
        if (dateText == null || dateText.isBlank()) return null;
        String t = dateText.trim().replaceAll("\\s+"," ");
        try { return LocalDate.parse(t, DateTimeFormatter.ISO_LOCAL_DATE); } catch (DateTimeParseException ignored) {}
        int y = LocalDate.now().getYear();
        for (String p : new String[]{"d MMM", "d MMMM"}) {
            try { return LocalDate.parse(t + " " + y, DateTimeFormatter.ofPattern(p, Locale.ENGLISH)); }
            catch (DateTimeParseException ignored) {}
        }
        return null;
    }
}
