package currency.comparators;

import java.util.Comparator;
import java.util.Currency;

/**
 * Compares currencies according to their 3-digit codes (zero-padded as needed)
 * in the ISO-4217 standard. To compare currencies according to their 3-letter
 * codes, use {@link LetterCodeComparator}.
 * <p>For reference, the French gold franc (XFO) has numeric code 000, and the
 * unknown currency (XXX) has numeric code 999.</p>
 * @author Alonso del Arte
 */
public class NumericCodeComparator implements Comparator<Currency> {

    /**
     * Compares currencies according to their 3-digit codes in the ISO-4217
     * standard. A currency's 3-digit code is given by the {@code Currency}
     * instance's {@code getNumericCode()} function.
     * @param currencyA The first currency to compare. For example, the Armenian
     * dram (AMD), which has numeric code 051.
     * @param currencyB The second currency to compare. For example, the South
     * African rand (ZAR), which has numeric code 710.
     * @return &minus;1 or less if {@code currencyA} has a lower 3-digit
     * currency code than {@code currencyB}, 1 or greater if {@code currencyA}
     * has a higher 3-digit currency code than {@code currencyB}, or 0 if {@code
     * currencyA} and {@code currencyB} are the same currency. In the example
     * with the Armenian dram (051) for {@code currencyA} and the South African
     * rand (710) for {@code currencyB}, this function will return &minus;1,
     * &minus;659 or any other negative integer. If we switch these two example
     * currencies, this function will return 1, 659 or any other positive
     * integer.
     * @throws NullPointerException If either {@code currencyA} or {@code
     * currencyB} is null.
     */
    @Override
    public int compare(Currency currencyA, Currency currencyB) {
        return Integer.compare(currencyA.getNumericCode(),
                currencyB.getNumericCode());
    }

}
