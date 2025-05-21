package accounts.transactions;

import static accounts.transactions.TransactionTest.RANDOM;
import currency.CurrencyAmount;
import static currency.CurrencyChooser.chooseCurrency;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

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

    static Object passThrough(Object obj) {
        return obj;
    }

    static Object provideNull() {
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
    void testEquals() {
        System.out.println("equals");
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment someComment = new Comment(text, currency, date);
        Comment sameComment = new Comment(text, currency, date);
        assertEquals(someComment, sameComment);
    }

    @Test
    void testNotEqualsDiffCurrency() {
        String text = makeComment();
        Currency currencyA = chooseCurrency();
        Currency currencyB = chooseCurrency();
        LocalDateTime date = LocalDateTime.now();
        Comment commentA = new Comment(text, currencyA, date);
        Comment commentB = new Comment(text, currencyB, date);
        String message = "Comment A is funded in " + currencyA.getDisplayName()
                + " (" + currencyA.getCurrencyCode()
                + "), currency B is funded in " + currencyB.getDisplayName()
                + " (" + currencyB.getCurrencyCode() + ")";
        assertNotEquals(commentA, commentB, message);
    }

    @Test
    void testNotEqualsDiffDate() {
        String text = makeComment();
        Currency currency = chooseCurrency();
        LocalDateTime dateA = LocalDateTime.now();
        LocalDateTime dateB = dateA.plusHours(RANDOM.nextInt(72))
                .plusMinutes(15);
        Comment commentA = new Comment(text, currency, dateA);
        Comment commentB = new Comment(text, currency, dateB);
        String message = "Comment A is dated " + dateA + ", comment B is dated "
                + dateB;
        assertNotEquals(commentA, commentB, message);
    }

    @Test
    void testHashCode() {
        System.out.println("hashCode");
        int capacity = RANDOM.nextInt(256) + 4;
        Set<Comment> comments = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        for (int i = 0; i < capacity; i++) {
            String text = makeComment();
            Currency currency = chooseCurrency();
            LocalDateTime date = LocalDateTime.now().minusHours(i);
            Comment comment = new Comment(text, currency, date);
            comments.add(comment);
            hashes.add(comment.hashCode());
        }
        int expected = comments.size();
        int actual = hashes.size();
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
