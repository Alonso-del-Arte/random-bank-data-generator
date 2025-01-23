package currency;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyAmountTest {

    public static final Random RANDOM = new Random();

    private static final Currency DOLLARS = Currency.getInstance(Locale.US);

    @Test
    void testToStringZeroToNineCentsOfDollar() {
        for (short cents = 0; cents < 10; cents++) {
            CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
            String expected = "$0.0" + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringTenToNinetyNineCentsOfDollar() {
        for (short cents = 10; cents < 100; cents++) {
            CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
            String expected = "$0." + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testConstructorRejectsNullCurrency() {
        int centsAmount = RANDOM.nextInt();
        String message = "Trying to instantiate " + centsAmount
                + " of null currency should cause an exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyAmount badAmount = new CurrencyAmount(centsAmount, null);
            System.out.println(message + ", not created instance "
                    + badAmount.getClass().getName() + '@'
                    + Integer.toHexString(badAmount.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsPseudocurrency() {
        int centsAmount = RANDOM.nextInt();
        Currency badCurrency = CurrencyChooser.choosePseudocurrency();
        String displayName = badCurrency.getDisplayName();
        String currencyCode = badCurrency.getCurrencyCode();
        String message = "Trying to instantiate " + centsAmount
                + " with pseudocurrency " + displayName + " (" + currencyCode
                + ") should cause an exception";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            CurrencyAmount badAmount = new CurrencyAmount(centsAmount,
                    badCurrency);
            System.out.println(message + ", not created instance "
                    + badAmount.getClass().getName() + '@'
                    + Integer.toHexString(badAmount.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should include currency code "
                + currencyCode + " for " + displayName;
        assert excMsg.contains(currencyCode) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

}