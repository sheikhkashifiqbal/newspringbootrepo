package com.car.carservices.util;

import java.util.HashMap;
import java.util.Map;

public final class PRVinDecoder {
    private static final Map<String, String> WMI_TO_BRAND = new HashMap<>();
    static {
        // Extend to your brands
        WMI_TO_BRAND.put("JTD", "Toyota");
        WMI_TO_BRAND.put("JT2", "Toyota");
        WMI_TO_BRAND.put("1HG", "Honda");
        WMI_TO_BRAND.put("JHM", "Honda");
        WMI_TO_BRAND.put("1FA", "Ford");
        WMI_TO_BRAND.put("3FA", "Ford");
        WMI_TO_BRAND.put("WDB", "Mercedes");
        WMI_TO_BRAND.put("WDC", "Mercedes");
        WMI_TO_BRAND.put("WVW", "Volkswagen");
    }
    private PRVinDecoder(){}

    public static String brandFromVin(String vin) {
        if (vin == null || vin.length() < 3) return null;
        return WMI_TO_BRAND.get(vin.substring(0,3).toUpperCase());
    }

    /** Use VIN positions 4..8 (VDS) as a fuzzy model code */
    public static String modelCodeFromVin(String vin) {
        if (vin == null || vin.length() < 8) return null;
        return vin.substring(3, 8).toUpperCase();
    }
}
