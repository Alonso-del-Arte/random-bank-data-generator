package currency;

import static currency.CurrencyAmountTest.RANDOM;

import java.util.Currency;

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

    @Test
    void testGetAmountAOnInstanceFromFirstAuxConstructor() {
        CurrencyAmount expected = makeAmount();
        CurrencyAmount amountB = makeAmountDiffCurrency(expected.getCurrency());
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(expected, amountB);
        CurrencyAmount actual = instance.getAmountA();
        String message = "Expecting amount of "
                + expected.getCurrency().getDisplayName() + " ("
                + expected.getCurrency().getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    // TODO: Write tests for getAmountA() on instances constructed from
    //  second auxiliary constructor

    @Test
    void testGetAmountB() {
        System.out.println("getAmountB");
        CurrencyAmount amountA = makeAmount();
        CurrencyAmount expected = makeAmountDiffCurrency(amountA.getCurrency());
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(amountA, expected,
                DEFAULT_MESSAGE);
        CurrencyAmount actual = instance.getAmountB();
        String message = "Expecting amount of "
                + expected.getCurrency().getDisplayName() + " ("
                + expected.getCurrency().getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    // TODO: Write tests for getAmountB() on instances constructed from
    //  auxiliary constructors

    @Test
    void testGetCurrencyA() {
        System.out.println("getCurrencyA");
        CurrencyAmount amountA = makeAmount();
        Currency expected = amountA.getCurrency();
        CurrencyAmount amountB = makeAmountDiffCurrency(expected);
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(amountA, amountB,
                DEFAULT_MESSAGE);
        Currency actual = instance.getCurrencyA();
        String message = "Currency of " + amountA + " should be "
                + expected.getDisplayName() + " (" + expected.getCurrencyCode()
                + ")";
        assertEquals(expected, actual, message);
    }

    // TODO: Write tests for getCurrencyA() on instances from auxiliary
    //  constructors

    @Test
    void testGetCurrencyB() {
        System.out.println("getCurrencyB");
        CurrencyAmount amountA = makeAmount();
        CurrencyAmount amountB = makeAmountDiffCurrency(amountA.getCurrency());
        CurrencyConversionNeededException instance
                = new CurrencyConversionNeededException(amountA, amountB,
                DEFAULT_MESSAGE);
        Currency expected = amountB.getCurrency();
        Currency actual = instance.getCurrencyB();
        String message = "Currency of " + amountB + " should be "
                + expected.getDisplayName() + " (" + expected.getCurrencyCode()
                + ")";
        assertEquals(expected, actual, message);
    }

    // TODO: Write tests for getCurrencyB() on instances from auxiliary
    //  constructors

    @Test
    void testPrimaryConstructorRejectsNullMessage() {
        CurrencyAmount amountA = makeAmount();
        CurrencyAmount amountB = makeAmountDiffCurrency(amountA.getCurrency());
        String message = "Null message should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(amountA, amountB,
                    null);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testPrimaryConstructorRejectsNullAmountA() {
        CurrencyAmount amountB = makeAmount();
        String message = "Null amountA should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(null, amountB,
                    DEFAULT_MESSAGE);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testPrimaryConstructorRejectsNullAmountB() {
        CurrencyAmount amountA = makeAmount();
        String message = "Null amountA should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(amountA, null,
                    DEFAULT_MESSAGE);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testFirstAuxConstructorRejectsNullAmountA() {
        CurrencyAmount amountB = makeAmount();
        String message = "Null amountA should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(null, amountB);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testFirstAuxConstructorRejectsNullAmountB() {
        CurrencyAmount amountA = makeAmount();
        String message = "Null amountB should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(amountA,
                    (CurrencyAmount) null);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testSecondAuxConstructorRejectsNullAmount() {
        Currency currency = CurrencyChooser.chooseCurrency();
        String message = "Null amountA should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(null, currency);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testSecondAuxConstructorRejectsNullCurrency() {
        CurrencyAmount amountA = makeAmount();
        String message = "Null currency should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyConversionNeededException instance
                    = new CurrencyConversionNeededException(amountA,
                    (Currency) null);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(instance.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
