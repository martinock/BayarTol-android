package com.upiki.bayartol.util;

/**
 * Utility need in BayarTol app.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.03
 */

public class BayarTolUtil {

    /**
     * A method for generating barcode raw string.
     * @param username user name
     * @param method payment method chosen by user
     * @param isBusinessTrip business trip mark
     * @param balance amount of money from user
     * @return raw string to generate barcode
     */
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

    /**
     * A method to format a number into Indonesian
     * currency
     * @param amount the number to be format
     * @return formatted amount
     */
    public static String currencyFormatter(
            long amount
    ) {
        String convertedAmount = String.valueOf(amount);
        if (convertedAmount.isEmpty()) {
            return "";
        }
        int firstSeparatorIndex = convertedAmount.length() % 3;
        String formattedAmount = "";
        if (firstSeparatorIndex != 0) {
            formattedAmount = convertedAmount.substring(
                    0, firstSeparatorIndex)
                    + ".";
        }
        int i = firstSeparatorIndex;
        while (i + 3 < convertedAmount.length()) {
            formattedAmount = formattedAmount
                    + convertedAmount.substring(i, i + 3)
                    + ".";
            i = i + 3;
        }
        formattedAmount = formattedAmount
                + convertedAmount.substring(i, i + 3);

        return "Rp " + formattedAmount;
    }
}
