// com/car/carservices/util/LocationUtil.java
package com.car.carservices.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocationUtil {
    private LocationUtil(){}

    private static final Pattern AT_PATTERN = Pattern.compile("@(-?\\d+\\.?\\d*),(-?\\d+\\.?\\d*)");
    private static final Pattern Q_PATTERN  = Pattern.compile("(?:[?&])(q|query)=(-?\\d+\\.?\\d*),(-?\\d+\\.?\\d*)", Pattern.CASE_INSENSITIVE);

    public static Double[] extractLatLon(String location) {
        if (location == null) return null;
        Matcher m1 = AT_PATTERN.matcher(location);
        if (m1.find()) return new Double[]{Double.valueOf(m1.group(1)), Double.valueOf(m1.group(2))};
        Matcher m2 = Q_PATTERN.matcher(location);
        if (m2.find()) return new Double[]{Double.valueOf(m2.group(2)), Double.valueOf(m2.group(3))};
        return null;
        // keep null if the URL has no embedded coordinates
    }
}
