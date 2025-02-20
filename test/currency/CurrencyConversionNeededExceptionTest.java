package currency;

import static currency.CurrencyAmountTest.RANDOM;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyConversionNeededExceptionTest {

    private static final String DEFAULT_MESSAGE = "FOR TESTING PURPOSES ONLY";

    private static CurrencyAmount makeAmount() {
        int centsAmount = RANDOM.nextInt();
        Currency currency = CurrencyChooser.chooseCurrency();
        return new CurrencyAmount(centsAmount, currency);
    }

    private static CurrencyAmount makeAmountDiffCurrency(Currency currency) {
        int centsAmount = RANDOM.nextInt();
        Currency other = CurrencyChooser.chooseCurrencyOtherThan(currency);
        return new CurrencyAmount(centsAmount, other);
    }

    @Test
    void testGetMessage() {
        System.out.println("getMessage");
        String expected = DEFAULT_MESSAGE + " " + RANDOM.nextInt();
        CurrencyAmount amountA = makeAmount();
        CurrencyAmount amountB = makeAmountDiffCurrency(amountA.getCurrency());
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(amountA, amountB,
                expected);
        String actual = instance.getMessage();
        assertEquals(expected, actual);
    }

    // TODO: Write tests for getMessage() on instances constructed from
    //  auxiliary constructors

    @Test
    void testGetAmountA() {
        System.out.println("getAmountA");
        CurrencyAmount expected = makeAmount();
        CurrencyAmount amountB = makeAmountDiffCurrency(expected.getCurrency());
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(expected, amountB,
                DEFAULT_MESSAGE);
        CurrencyAmount actual = instance.getAmountA();
        String message = "Expecting amount of "
                + expected.getCurrency().getDisplayName() + " ("
                + expected.getCurrency().getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

}
