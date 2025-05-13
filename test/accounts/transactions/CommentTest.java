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
