package currency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
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
    public void testToStringNegativeUnitsPlusTenToNinetyNineCents() {
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
    void testToStringCurrencyNoCentsOrMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(0);
        int units = RANDOM.nextInt(8192);
        CurrencyAmount instance = new CurrencyAmount(units, currency);
        String expected = currency.getSymbol() + units;
        String actual = instance.toString();
        String message = units + " units of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ')';
        assertEquals(expected, actual, message);
    }

    @Test
    void testToStringCurrencyNegativeNoCentsOrMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(0);
        int units = -RANDOM.nextInt(8192) - 1;
        CurrencyAmount instance = new CurrencyAmount(units, currency);
        String expected = MINUS_SIGN + currency.getSymbol() + (-units);
        String actual = instance.toString();
        String message = units + " units of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ')';
        assertEquals(expected, actual, message);
    }

    @Test
    void testToStringZeroToNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.00";
        for (short milles = 0; milles < 10; milles++) {
            CurrencyAmount amount = new CurrencyAmount(milles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + milles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringTenToNinetyNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.0";
        for (short milles = 10; milles < 100; milles++) {
            CurrencyAmount amount = new CurrencyAmount(milles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + milles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringOneHundredToNineHundredNinetyNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.";
        for (short milles = 100; milles < 1000; milles++) {
            CurrencyAmount amount = new CurrencyAmount(milles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + milles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testUnitsOfCurrencyPlusZeroToNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        int units = RANDOM.nextInt(1, 8192);
        int unitsInMilles = units * 1000;
        String symbol = currency.getSymbol();
        String partial = symbol + units + ".00";
        for (short milles = 0; milles < 10; milles++) {
            int totalMilles = unitsInMilles + milles;
            CurrencyAmount amount = new CurrencyAmount(totalMilles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + totalMilles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testUnitsOfCurrencyPlusTenToNinetyNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        int units = RANDOM.nextInt(1, 8192);
        int unitsInMilles = units * 1000;
        String symbol = currency.getSymbol();
        String partial = symbol + units + ".0";
        for (short milles = 10; milles < 100; milles++) {
            int totalMilles = unitsInMilles + milles;
            CurrencyAmount amount = new CurrencyAmount(totalMilles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + totalMilles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testUnitsOfCurrencyPlusOneHundredToNineHundredNinetyNineMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        int units = RANDOM.nextInt(1, 8192);
        int unitsInMilles = units * 1000;
        String symbol = currency.getSymbol();
        String partial = symbol + units + ".";
        for (short milles = 100; milles < 1000; milles++) {
            int totalMilles = unitsInMilles + milles;
            CurrencyAmount amount = new CurrencyAmount(totalMilles, currency);
            String expected = partial + milles;
            String actual = amount.toString();
            String message = "toString() for " + totalMilles + " milles of "
                    + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    // TODO: Write tests for negative amounts of currencies with milles

    @Test
    void testToStringZeroToNineDivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.000";
        for (short tenThousandths = 0; tenThousandths < 10; tenThousandths++) {
            CurrencyAmount amount = new CurrencyAmount(tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringTenToNinetyNineDivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.00";
        for (short tenThousandths = 10; tenThousandths < 100;
             tenThousandths++) {
            CurrencyAmount amount = new CurrencyAmount(tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToString100To999DivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.0";
        for (short tenThousandths = 100; tenThousandths < 1000;
             tenThousandths++) {
            CurrencyAmount amount = new CurrencyAmount(tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToString1000To9990DivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        String partial = symbol + "0.";
        for (short tenThousandths = 1000; tenThousandths < 10000;
             tenThousandths++) {
            CurrencyAmount amount = new CurrencyAmount(tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlusZeroToNineDivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        int units = RANDOM.nextInt(1, 100);
        String partial = symbol + units + ".000";
        int unitsToSubunits = units * 10000;
        for (short tenThousandths = 0; tenThousandths < 10; tenThousandths++) {
            CurrencyAmount amount
                    = new CurrencyAmount(unitsToSubunits + tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlusTenToNinetyNineDivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        int units = RANDOM.nextInt(1, 100);
        String partial = symbol + units + ".00";
        int unitsToSubunits = units * 10000;
        for (short tenThousandths = 10; tenThousandths < 100;
             tenThousandths++) {
            CurrencyAmount amount
                    = new CurrencyAmount(unitsToSubunits + tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlus100To999DivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        int units = RANDOM.nextInt(1, 100);
        String partial = symbol + units + ".0";
        int unitsToSubunits = units * 10000;
        for (short tenThousandths = 100; tenThousandths < 1000;
             tenThousandths++) {
            CurrencyAmount amount
                    = new CurrencyAmount(unitsToSubunits + tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    @Test
    void testToStringUnitsPlus1000To9999DivisionsOfCurrencyDivides10000() {
        Currency currency = CurrencyChooser.chooseCurrency(4);
        String symbol = currency.getSymbol();
        int units = RANDOM.nextInt(1, 100);
        String partial = symbol + units + ".";
        int unitsToSubunits = units * 10000;
        for (short tenThousandths = 1000; tenThousandths < 10000;
             tenThousandths++) {
            CurrencyAmount amount
                    = new CurrencyAmount(unitsToSubunits + tenThousandths,
                    currency);
            String expected = partial + tenThousandths;
            String actual = amount.toString();
            String message = "toString() for " + tenThousandths
                    + " ten thousandths of " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ')';
            assertEquals(expected, actual, message);
        }
    }

    // TODO: Write toString() tests for negative amounts of currency with 4
    //  default fraction digits

    @Test
    void testGetAmountInSubunits() {
        int expected = RANDOM.nextInt();
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(expected, currency);
        long actual = amount.getAmountInSubunits();
        String message = "Getting amount " + amount + " of "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") in subunits";
        assertEquals(expected, actual, message);
    }

    // TODO: Write tests for getUnitAmount() and getChangeAmount()

    @Test
    void testGetCurrency() {
        System.out.println("getCurrency");
        Currency expected = CurrencyChooser.chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int centsAmount = RANDOM.nextInt(128000);
        CurrencyAmount amount = new CurrencyAmount(centsAmount, expected);
        Currency actual = amount.getCurrency();
        String message = "Currency of " + amount + " should be "
                + expected.getDisplayName();
        assertEquals(expected, actual, message);
    }

    @Test
    void testIsPositive() {
        System.out.println("isPositive");
        int centsAmount = RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode() + ") should be positive";
        assert amount.isPositive() : msg;
    }

    @Test
    void testZeroPositiveIsNot() {
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(0, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be positive";
        assert !amount.isPositive() : msg;
    }

    @Test
    void testNegativePositiveIsNot() {
        int centsAmount = -RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be positive";
        assert !amount.isPositive() : msg;
    }

    @Test
    void testIsNotNegative() {
        System.out.println("isNotNegative");
        int centsAmount = RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be negative";
        assert amount.isNotNegative() : msg;
    }

    @Test
    void testZeroIsNotNegative() {
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(0, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be negative";
        assert amount.isNotNegative() : msg;
    }

    @Test
    void testNegativeIsNotNegativeButItIs() {
        int centsAmount = -RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be not negative";
        assert !amount.isNotNegative() : msg;
    }

    @Test
    void testIsZero() {
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(0, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode() + ") should be zero";
        assert amount.isZero() : msg;
    }

    @Test
    void testPositiveIsZeroButIsNot() {
        int centsAmount = RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode() + ") should not be zero";
        assert !amount.isZero() : msg;
    }

    @Test
    void testNegativeIsZeroButIsNot() {
        int centsAmount = -RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode() + ") should not be zero";
        assert !amount.isZero() : msg;
    }

    @Test
    void testIsNotPositive() {
        System.out.println("isNotPositive");
        int centsAmount = -RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be positive";
        assert amount.isNotPositive() : msg;
    }

    @Test
    void testIsNotPositiveButItIs() {
        int centsAmount = RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be not positive";
        assert !amount.isNotPositive() : msg;
    }

    @Test
    void testZeroIsNotPositive() {
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(0, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be positive";
        assert amount.isNotPositive() : msg;
    }

    @Test
    void testIsNegative() {
        System.out.println("isNegative");
        int centsAmount = -RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode() + ") should be negative";
        assert amount.isNegative() : msg;
    }

    @Test
    void testIsNegativeButIsActuallyPositive() {
        int centsAmount = RANDOM.nextInt(1, 128000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be negative";
        assert !amount.isNegative() : msg;
    }

    @Test
    void testIsNegativeButIsActuallyZero() {
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(0, currency);
        String msg = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") should not be negative";
        assert !amount.isNegative() : msg;
    }

    private static Object passThrough(Object obj) {
        return obj;
    }

    @Test
    void testReferentialEquality() {
        Currency currency = CurrencyChooser.chooseCurrency();
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        Object sameRef = passThrough(amount);
        assertEquals(amount, sameRef);
    }

    private static Object provideNull() {
        return null;
    }

    @Test
    void testNotEqualsNull() {
        Currency currency = CurrencyChooser.chooseCurrency();
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        Object obj = provideNull();
        String msg = "Amount " + amount + " of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") should not equal null";
        assert !amount.equals(obj) : msg;
    }

    @Test
    void testNotEqualsDiffClass() {
        Currency currency = CurrencyChooser.chooseCurrency();
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(centsAmount, currency);
        CurrencyAmount amountDiffClass
                = new CurrencyAmount(centsAmount, currency) {};
        String msg = "Amount " + amount + " of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") of runtime class " + amount.getClass().getName()
                + " should not equal same amount of class "
                + amountDiffClass.getClass().getName();
        assert !amount.equals(amountDiffClass) : msg;
    }

    @Test
    void testEquals() {
        System.out.println("equals");
        Currency currency = CurrencyChooser.chooseCurrency();
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount someAmount = new CurrencyAmount(centsAmount, currency);
        CurrencyAmount sameAmount = new CurrencyAmount(centsAmount, currency);
        String message = "Same amount of same currency, "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ")";
        assertEquals(someAmount, sameAmount, message);
    }

    @Test
    void testNotEqualDiffAmount() {
        Currency currency = CurrencyChooser.chooseCurrency();
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount someAmount = new CurrencyAmount(centsAmount, currency);
        CurrencyAmount diffAmount = new CurrencyAmount(centsAmount + 1,
                currency);
        String message = "Different amounts of same currency, "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ")";
        assertNotEquals(someAmount, diffAmount, message);
    }

    @Test
    void testNotEqualDiffCurrency() {
        Currency currencyA = CurrencyChooser.chooseCurrency();
        Currency currencyB = CurrencyChooser.chooseCurrencyOtherThan(currencyA);
        int centsAmount = RANDOM.nextInt();
        CurrencyAmount amountA = new CurrencyAmount(centsAmount, currencyA);
        CurrencyAmount amountB = new CurrencyAmount(centsAmount, currencyB);
        String message = "Same amount of different currencies, "
                + currencyA.getDisplayName() + " (" + currencyA.getCurrencyCode()
                + ") and " + currencyB.getDisplayName() + " ("
                + currencyB.getCurrencyCode() + ")";
        assertNotEquals(amountA, amountB, message);
    }

    @Test
    void testHashCode() {
        System.out.println("hashCode");
        int numberOfCurrencies = RANDOM.nextInt(16) + 4;
        int numberOfAmountsPerCurrency = RANDOM.nextInt(128) + 32;
        int capacity = numberOfCurrencies * numberOfAmountsPerCurrency;
        Set<Currency> currencies = new HashSet<>(numberOfCurrencies);
        while (currencies.size() < numberOfCurrencies) {
            currencies.add(CurrencyChooser.chooseCurrency());
        }
        Set<CurrencyAmount> amounts = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        int amountInSubunits = -524288;
        for (int i = 0; i < numberOfAmountsPerCurrency; i++) {
            amountInSubunits += RANDOM.nextInt(1024);
            for (Currency currency : currencies) {
                CurrencyAmount amount = new CurrencyAmount(amountInSubunits,
                        currency);
                amounts.add(amount);
                hashes.add(amount.hashCode());
            }
        }
        String message = "Given " + numberOfCurrencies + " currencies and "
                + numberOfAmountsPerCurrency
                + " amounts per currency, there should be " + capacity
                + " distinct hashes";
        int expected = amounts.size();
        int actual = hashes.size();
        assertEquals(expected, actual, message);
    }

    @Test
    void testNegativeOneOf() {
        System.out.println("negativeOneOf");
        Currency currency = CurrencyChooser.chooseCurrency(2);
        CurrencyAmount expected = new CurrencyAmount(-100, currency);
        CurrencyAmount actual = CurrencyAmount.negativeOneOf(currency);
        String message = "Getting negative one of " + currency.getDisplayName()
                + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testNegativeOneOfSubdivMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        CurrencyAmount expected = new CurrencyAmount(-1000, currency);
        CurrencyAmount actual = CurrencyAmount.negativeOneOf(currency);
        String message = "Getting negative one of " + currency.getDisplayName()
                + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testNegativeOneOfNoSubdivs() {
        Currency currency = CurrencyChooser.chooseCurrency(0);
        CurrencyAmount expected = new CurrencyAmount(-1, currency);
        CurrencyAmount actual = CurrencyAmount.negativeOneOf(currency);
        String message = "Getting negative one of " + currency.getDisplayName()
                + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testZeroOf() {
        System.out.println("zeroOf");
        Currency currency = CurrencyChooser.chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        CurrencyAmount expected = new CurrencyAmount(0, currency);
        CurrencyAmount actual = CurrencyAmount.zeroOf(currency);
        String message = "Getting zero of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testOneOf() {
        System.out.println("oneOf");
        Currency currency = CurrencyChooser.chooseCurrency(2);
        CurrencyAmount expected = new CurrencyAmount(100, currency);
        CurrencyAmount actual = CurrencyAmount.oneOf(currency);
        String message = "Getting one of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testOneOfSubdivMilles() {
        Currency currency = CurrencyChooser.chooseCurrency(3);
        CurrencyAmount expected = new CurrencyAmount(1000, currency);
        CurrencyAmount actual = CurrencyAmount.oneOf(currency);
        String message = "Getting one of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testOneOfNoSubdivs() {
        Currency currency = CurrencyChooser.chooseCurrency(0);
        CurrencyAmount expected = new CurrencyAmount(1, currency);
        CurrencyAmount actual = CurrencyAmount.oneOf(currency);
        String message = "Getting one of " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testPlusShouldRejectNullAddend() {
        int bound = 32768;
        int addendSubunits = RANDOM.nextInt(1, bound);
        Currency currency = CurrencyChooser.chooseCurrency();
        Currency currencyB = CurrencyChooser.chooseCurrencyOtherThan(currency);
        CurrencyAmount addend = new CurrencyAmount(addendSubunits, currency);
        String message = "Adding up " + addend + " of currency "
                + currency.getDisplayName() + " ("
                + currency.getCurrencyCode()
                + ") to null should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyAmount badResult = addend.plus(null);
            System.out.println(message + ", not given result " + badResult.toString());
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testPlus() {
        System.out.println("plus");
        int bound = 32768;
        int addendASubunits = RANDOM.nextInt(1, bound);
        int addendBSubunits = RANDOM.nextInt(1, bound);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount addendA = new CurrencyAmount(addendASubunits, currency);
        CurrencyAmount addendB = new CurrencyAmount(addendBSubunits, currency);
        int expSubunits = addendASubunits + addendBSubunits;
        CurrencyAmount expected = new CurrencyAmount(expSubunits, currency);
        CurrencyAmount actual = addendA.plus(addendB);
        String message = "Adding up " + addendA + " and " + addendB
                + " of currency " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testPlusMismatchedCurrency() {
        int bound = 32768;
        int addendASubunits = RANDOM.nextInt(1, bound);
        int addendBSubunits = RANDOM.nextInt(1, bound);
        Currency currencyA = CurrencyChooser.chooseCurrency();
        Currency currencyB = CurrencyChooser.chooseCurrencyOtherThan(currencyA);
        CurrencyAmount addendA = new CurrencyAmount(addendASubunits, currencyA);
        CurrencyAmount addendB = new CurrencyAmount(addendBSubunits, currencyB);
        String message = "Adding up " + addendA + " of currency "
                + currencyA.getDisplayName() + " ("
                + currencyA.getCurrencyCode() + ")" + " and " + addendB
                + " of currency " + currencyB.getDisplayName() + " ("
                + currencyB.getCurrencyCode() + ") should cause exception";
        CurrencyConversionNeededException exc
                = assertThrows(CurrencyConversionNeededException.class, () -> {
            CurrencyAmount badResult = addendA.plus(addendB);
            System.out.println(message + ", not given result " + badResult.toString());
        }, message);
        String excMsg = exc.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
        boolean holdAmounts = (addendA.equals(exc.getAmountA())
                && addendB.equals(exc.getAmountB()))
                || (addendA.equals(exc.getAmountB())
                && addendB.equals(exc.getAmountA()));
        assert holdAmounts : message;
    }

    @Test
    void testMinusShouldRejectNullSubtrahend() {
        int bound = 32768;
        int addendSubunits = RANDOM.nextInt(1, bound);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount minuend = new CurrencyAmount(addendSubunits, currency);
        String message = "Subtracting null from " + minuend + " of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyAmount badResult = minuend.minus(null);
            System.out.println(message + ", not given result "
                    + badResult.toString());
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testMinus() {
        System.out.println("minus");
        int bound = 32768;
        int minuendSubunits = RANDOM.nextInt(2, bound);
        int subtrahendSubunits = RANDOM.nextInt(1, minuendSubunits);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount minuend = new CurrencyAmount(minuendSubunits, currency);
        CurrencyAmount subtrahend = new CurrencyAmount(subtrahendSubunits, currency);
        int expSubunits = minuendSubunits - subtrahendSubunits;
        CurrencyAmount expected = new CurrencyAmount(expSubunits, currency);
        CurrencyAmount actual = minuend.minus(subtrahend);
        String message = "Subtracting " + subtrahend + " from " + minuend
                + " of currency " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testMinusMismatchedCurrency() {
        int bound = 32768;
        int minuendSubunits = RANDOM.nextInt(2, bound);
        int subtrahendSubunits = RANDOM.nextInt(1, minuendSubunits);
        Currency currencyA = CurrencyChooser.chooseCurrency();
        Currency currencyB = CurrencyChooser.chooseCurrencyOtherThan(currencyA);
        CurrencyAmount minuend = new CurrencyAmount(minuendSubunits, currencyA);
        CurrencyAmount subtrahend = new CurrencyAmount(subtrahendSubunits,
                currencyB);
        String message = "Subtracting " + subtrahend + " of currency "
                + currencyB.getDisplayName() + " ("
                + currencyB.getCurrencyCode() + ") from " + minuend
                + " of currency " + currencyA.getDisplayName() + " ("
                + currencyA.getCurrencyCode() + ") should cause exception";
        CurrencyConversionNeededException exc
                = assertThrows(CurrencyConversionNeededException.class, () -> {
            CurrencyAmount badResult = minuend.minus(subtrahend);
            System.out.println(message + ", not given result " + badResult.toString());
        }, message);
        String excMsg = exc.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
        boolean holdAmounts = (minuend.equals(exc.getAmountA())
                && subtrahend.equals(exc.getAmountB()))
                || (minuend.equals(exc.getAmountB())
                && subtrahend.equals(exc.getAmountA()))
                || (minuend.equals(exc.getAmountA())
                && subtrahend.equals(exc.getAmountB().negate()))
                || (minuend.equals(exc.getAmountB())
                && subtrahend.equals(exc.getAmountA().negate()));
        assert holdAmounts : message;
    }

    @Test
    void testNegate() {
        System.out.println("negate");
        Currency currency = CurrencyChooser.chooseCurrency();
        int subdivsAmount = RANDOM.nextInt(262144) + 16;
        CurrencyAmount amount = new CurrencyAmount(subdivsAmount, currency);
        CurrencyAmount expected = new CurrencyAmount(-subdivsAmount, currency);
        CurrencyAmount actual = amount.negate();
        String message = "Negating " + amount + " of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testNegateNegativeIsPositive() {
        Currency currency = CurrencyChooser.chooseCurrency();
        int subdivsAmount = -RANDOM.nextInt(262144) - 16;
        CurrencyAmount amount = new CurrencyAmount(subdivsAmount, currency);
        CurrencyAmount expected = new CurrencyAmount(-subdivsAmount, currency);
        CurrencyAmount actual = amount.negate();
        String message = "Negating " + amount + " of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testCompareTo() {
        System.out.println("compareTo");
        int capacity = RANDOM.nextInt(16) + 4;
        List<CurrencyAmount> expected = new ArrayList<>(capacity);
        int subunits = -524288 - RANDOM.nextInt(12);
        int high = -subunits + RANDOM.nextInt(12);
        int bound = 2 * high / capacity;
        Currency currency = CurrencyChooser.chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        while (subunits < high) {
            CurrencyAmount amount = new CurrencyAmount(subunits, currency);
            expected.add(amount);
            subunits += (RANDOM.nextInt(bound) + 1);
        }
        List<CurrencyAmount> actual = new ArrayList<>(expected);
        Collections.shuffle(actual, RANDOM);
        Collections.sort(actual);
        String message = "Sorting amounts of " + currency.getDisplayName()
                + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testCompareToMismatchedCausesException() {
        int capacity = RANDOM.nextInt(16) + 4;
        List<CurrencyAmount> amounts = new ArrayList<>(capacity);
        int subunits = -524288 - RANDOM.nextInt(12);
        int high = -subunits + RANDOM.nextInt(12);
        int bound = 2 * high / capacity;
        Currency prevCurrency = CurrencyChooser.chooseCurrency();
        while (subunits < high) {
            Currency currency
                    = CurrencyChooser.chooseCurrencyOtherThan(prevCurrency);
            CurrencyAmount amount = new CurrencyAmount(subunits, currency);
            amounts.add(amount);
            subunits += (RANDOM.nextInt(bound) + 1);
            prevCurrency = currency;
        }
        String message = "Trying to sort " + amounts
                + " should cause exception";
        CurrencyConversionNeededException exc
                = assertThrows(CurrencyConversionNeededException.class, () -> {
                    Collections.sort(amounts);
        }, message);
        String excMsg = exc.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        System.out.println("\"" + excMsg + "\"");
        CurrencyAmount amountA = exc.getAmountA();
        CurrencyAmount amountB = exc.getAmountB();
        String contentsMsg = "List of amounts should contain "
                + amountA.toString() + " and " + amountB.toString()
                + " or their inverses";
        assert amounts.contains(amountA) || amounts.contains(amountA.negate())
                : contentsMsg;
        assert amounts.contains(amountB) || amounts.contains(amountB.negate())
                : contentsMsg;
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