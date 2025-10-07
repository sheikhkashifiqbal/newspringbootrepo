// com/car/carservices/util/DayNameUtil.java
package com.car.carservices.util;

import java.util.*;

public final class DayNameUtil {
    private DayNameUtil(){}

    private static final Map<String,String> CANON = Map.ofEntries(
        Map.entry("mon","monday"), Map.entry("monday","monday"), Map.entry("moday","monday"),
        Map.entry("tue","tuesday"), Map.entry("tuesday","tuesday"), Map.entry("tueday","tuesday"),
        Map.entry("wed","wednesday"), Map.entry("wednesday","wednesday"),
        Map.entry("thu","thursday"), Map.entry("thur","thursday"), Map.entry("thursday","thursday"),
        Map.entry("fri","friday"),   Map.entry("friday","friday"),
        Map.entry("sat","saturday"), Map.entry("saturday","saturday"),
        Map.entry("sun","sunday"),   Map.entry("sunday","sunday")
    );

    public static String normalize(String s) {
        if (s == null) return null;
        return CANON.getOrDefault(s.trim().toLowerCase(), null);
    }

    public static List<String> week() {
        return List.of("monday","tuesday","wednesday","thursday","friday","saturday","sunday");
    }
}
