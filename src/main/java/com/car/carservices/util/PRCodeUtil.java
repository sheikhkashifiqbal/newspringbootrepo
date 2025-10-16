// com/car/carservices/util/PRCodeUtil.java
package com.car.carservices.util;

import java.security.SecureRandom;

public final class PRCodeUtil {
    private static final SecureRandom RNG = new SecureRandom();
    private PRCodeUtil() {}

    public static String fourDigitCode() {
        int n = 1000 + RNG.nextInt(9000); // 1000..9999
        return Integer.toString(n);
    }
}
