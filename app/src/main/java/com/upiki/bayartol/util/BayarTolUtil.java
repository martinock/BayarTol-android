package com.upiki.bayartol.util;

/**
 * Utility need in BayarTol app.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.03
 */

public class BayarTolUtil {
    public static String barcodeStringGenerator(
            String username,
            String method,
            boolean isBusinessTrip,
            String balance
    ) {
        return username + "_"
                + method + "_"
                + String.valueOf(isBusinessTrip) + "_"
                + balance;
    }
}
