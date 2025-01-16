package currency;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CurrencyPairTest {

    @Test
    public void testGetFromCurrency() {
        System.out.println("getFromCurrency");
        Currency expected = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(expected);
        CurrencyPair instance = new CurrencyPair(expected, to);
        Currency actual = instance.getFromCurrency();
        String message = "Expected " + expected.getDisplayName() + ", got "
                + actual.getDisplayName();
        assertEquals(expected, actual, message);
    }

    @Test
    public void testGetToCurrency() {
        System.out.println("getToCurrency");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency expected = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair instance = new CurrencyPair(from, expected);
        Currency actual = instance.getToCurrency();
        String message = "Expected " + expected.getDisplayName() + ", got "
                + actual.getDisplayName();
        assertEquals(expected, actual, message);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair instance = new CurrencyPair(from, to);
        String expected = from.getCurrencyCode() + '_' + to.getCurrencyCode();
        String actual = instance.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testReferentialEquality() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair somePair = new CurrencyPair(from, to);
        assert somePair.equals(somePair) : "Instance should be equal to itself";
    }

    @Test
    public void testFlip() {
        System.out.println("flip");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair instance = new CurrencyPair(from, to);
        CurrencyPair expected = new CurrencyPair(to, from);
        CurrencyPair actual = instance.flip();
        String message = "Expected from " + from.getDisplayName() + " to "
                + to.getDisplayName() + " to flip to from "
                + to.getDisplayName() + " to " + from.getDisplayName();
        assertEquals(actual, expected, message);
    }

    @Test
    public void testConstructorRejectsNullFromCurrency() {
        Currency to = CurrencyChooser.chooseCurrency();
        String msg = "Currency pair with null From currency and "
                + to.getDisplayName() + " (" + to.getCurrencyCode()
                + ") should cause NPE";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyPair instance = new CurrencyPair(null, to);
            System.out.println(msg + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testConstructorRejectsNullToCurrency() {
        Currency from = CurrencyChooser.chooseCurrency();
        String msg = "Currency pair with " + from.getDisplayName() + " ("
                + from.getCurrencyCode()
                + ") and null To currency should cause NPE";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyPair instance = new CurrencyPair(from, null);
            System.out.println(msg + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}