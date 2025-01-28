package currency;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyAmountTest {

    public static final Random RANDOM = new Random();

    private static final Currency DOLLARS = Currency.getInstance(Locale.US);

    private static final char MINUS_SIGN = '−';

    /**
     * Criterion for a currency other than United States dollars with a symbol
     * other than its 3-letter ISO-4217 code and having two default fraction
     * digits.
     */
    private static final Predicate<Currency> CRITERION_A
            = currency -> !currency.equals(DOLLARS)
            && !currency.getSymbol().equals(currency.getCurrencyCode())
            && currency.getDefaultFractionDigits() == 2;

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
    void testToStringDollarsPlusZeroToNineCentsOfDollar() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        int dollarsInCents = dollarQty * 100;
        String partial = "$" + dollarQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            CurrencyAmount amount = new CurrencyAmount(dollarsInCents + cents,
                    DOLLARS);
            String expected = partial + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringDollarsPlusTenToNinetyNineCentsOfDollar() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        int dollarsInCents = dollarQty * 100;
        String partial = "$" + dollarQty + '.';
        for (short cents = 10; cents < 100; cents++) {
            CurrencyAmount amount = new CurrencyAmount(dollarsInCents + cents,
                    DOLLARS);
            String expected = partial + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testToStringNegativeDollarAmountPlusZeroToNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        int dollarsInCents = dollarQty * 100;
        String partial = MINUS_SIGN + "$" + dollarQty + ".0";
        for (short cents = 0; cents < 9; cents++) {
            CurrencyAmount amount = new CurrencyAmount(-dollarsInCents - cents,
                    DOLLARS);
            String expected = partial + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testToStringNegativeDollarAmountPlusTenToNinetyNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        int dollarsInCents = dollarQty * 100;
        String partial = MINUS_SIGN + "$" + dollarQty + '.';
        for (short cents = 10; cents < 99; cents++) {
            CurrencyAmount amount = new CurrencyAmount(-dollarsInCents - cents,
                    DOLLARS);
            String expected = partial + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringZeroToNineCentsOfOtherCurrency() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        for (short cents = 0; cents < 10; cents++) {
            CurrencyAmount amount = new CurrencyAmount(cents, currency);
            String expected = symbol + "0.0" + cents;
            String actual = amount.toString();
            String message = "toString() for " + cents + " cents of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringTenToNinetyNineCentsOfOtherCurrency() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        for (short cents = 10; cents < 100; cents++) {
            CurrencyAmount amount = new CurrencyAmount(cents, currency);
            String expected = symbol + "0." + cents;
            String actual = amount.toString();
            String message = "toString() for " + cents + " cents of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlusZeroToNineCentsOfOtherCurrency() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        int unitsQty = RANDOM.nextInt(1000) + 1;
        int unitsInCents = unitsQty * 100;
        String partial = symbol + unitsQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            CurrencyAmount amount = new CurrencyAmount(unitsInCents + cents, currency);
            String expected = partial + cents;
            String actual = amount.toString();
            String message = "toString() for " + unitsQty + " units and "
                    + cents + " cents of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlusTenToNinetyNineCentsOfOtherCurrency() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        int unitsQty = RANDOM.nextInt(1000) + 1;
        int unitsInCents = unitsQty * 100;
        String partial = symbol + unitsQty + ".";
        for (short cents = 10; cents < 100; cents++) {
            CurrencyAmount amount = new CurrencyAmount(unitsInCents + cents, currency);
            String expected = partial + cents;
            String actual = amount.toString();
            String message = "toString() for " + unitsQty + " units and "
                    + cents + " cents of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    public void testToStringNegativeUnitsOfOtherCurrencyPlusZeroToNineCents() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        int unitsQty = RANDOM.nextInt(1000) + 1;
        int unitsInCents = unitsQty * 100;
        String partial = MINUS_SIGN + symbol + unitsQty + ".0";
        for (short cents = 0; cents < 9; cents++) {
            CurrencyAmount amount = new CurrencyAmount(-unitsInCents - cents,
                    currency);
            String expected = partial + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testToStringNegativeUnitsOfOtherCurrencyPlusTenToNinetyNineCents() {
        Currency currency = CurrencyChooser.chooseCurrency(CRITERION_A);
        String symbol = currency.getSymbol();
        int unitsQty = RANDOM.nextInt(1000) + 1;
        int unitsInCents = unitsQty * 100;
        String partial = MINUS_SIGN + symbol + unitsQty + ".";
        for (short cents = 10; cents < 99; cents++) {
            CurrencyAmount amount = new CurrencyAmount(-unitsInCents - cents,
                    currency);
            String expected = partial + cents;
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