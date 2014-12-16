package eu.nohaapav.example.checkoutservice.utils;

import java.math.BigDecimal;

/**
 * Utility class to work with {@link BigDecimal} type.
 *
 * @author pavol.noha@gmail.com
 */
public final class CalculationUtils {

    private static final int DECIMALS = 2;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     * Return percentage amount from given one based on percentage value.
     * Rounding mode: ROUND_HALF_UP {@see BigDecimal}
     *
     * @param amount     original amount
     * @param percentage percentage value that should be taken from original amount
     * @return percentage amount from original one
     */
    public static BigDecimal percentage(final BigDecimal amount,
                                        final BigDecimal percentage) {
        final BigDecimal result = amount.multiply(percentage);
        return result.divide(HUNDRED, ROUNDING_MODE);
    }

    /**
     * Return round of given amount.
     * Scale: 2 decimal
     * Rounding mode: ROUND_HALF_UP {@see BigDecimal}
     *
     * @param amount original amount to be rounded
     * @return rounded amount
     */
    public static BigDecimal round(final BigDecimal amount) {
        return amount.setScale(DECIMALS, ROUNDING_MODE);
    }
}
