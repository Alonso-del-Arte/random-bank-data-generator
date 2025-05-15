package accounts.transactions;

import static accounts.transactions.TransactionTest.RANDOM;
import currency.CurrencyAmount;
import static currency.CurrencyChooser.chooseCurrency;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CommentTest {

    private static final String DEFAULT_TEXT = "For testing purposes only";

    private static String makeComment() {
        return "Awarded " + RANDOM.nextInt() + " bonus points";
    }

    @Test
    void testGetText() {
        System.out.println("getText");
        String expected = DEFAULT_TEXT + " : " + RANDOM.nextInt();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(expected, currency, date);
        String actual = instance.getText();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(DEFAULT_TEXT, currency, date);
        CurrencyAmount expected = CurrencyAmount.zeroOf(currency);
        CurrencyAmount actual = instance.getAmount();
        String message = "Comment for account funded in "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void testGetTimestamp() {
        System.out.println("getTimestamp");
        Currency currency = chooseCurrency();
        LocalDateTime expected = LocalDateTime.now();
        Comment instance = new Comment(DEFAULT_TEXT, currency, expected);
        LocalDateTime actual = instance.getTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        System.out.println("toString");
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(text, currency, date);
        String expected = "Comment: \"" + text + "\" " + date;
        String actual = instance.toString();
        assertEquals(expected, actual);
    }

    private static Object passThrough(Object obj) {
        return obj;
    }

    private static Object provideNull() {
        return null;
    }

    @Test
    void testReferentialEquality() {
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(text, currency, date);
        Object obj = passThrough(instance);
        assertEquals(instance, obj);
    }

    @Test
    void testNotEqualsNull() {
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(text, currency, date);
        Object obj = provideNull();
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffClass() {
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment instance = new Comment(text, currency, date);
        Object obj = passThrough(this);
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffText() {
        String textA = makeComment();
        String textB = textA.toUpperCase();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment commentA = new Comment(textA, currency, date);
        Comment commentB = new Comment(textB, currency, date);
        assertNotEquals(commentA, commentB);
    }

    @Test
    void testConstructorRejectsNullText() {
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null text";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Comment badInstance = new Comment(null, currency, date);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullCurrency() {
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null currency";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Comment badInstance = new Comment(DEFAULT_TEXT,null, date);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullDate() {
        Currency currency = chooseCurrency();
        String message = "Constructor should reject currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") with null date";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Comment badInstance = new Comment(DEFAULT_TEXT, currency, null);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
