package accounts.transactions;

import static accounts.transactions.TransactionTest.RANDOM;
import currency.CurrencyAmount;
import static currency.CurrencyChooser.chooseCurrency;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class WithdrawalTest {

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = -RANDOM.nextInt(10000) - 1;
        CurrencyAmount expected = new CurrencyAmount(amountInSubunits, currency);
        Withdrawal instance = new Withdrawal(expected, LocalDateTime.now());
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTimestamp() {
        System.out.println("getTimestamp");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = -RANDOM.nextInt(10000) - 1;
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime expected = LocalDateTime.now()
                .minusHours(RANDOM.nextInt(72));
        Withdrawal instance = new Withdrawal(amount, expected);
        LocalDateTime actual = instance.getTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        System.out.println("toString");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = -RANDOM.nextInt(10000) - 1;
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(72));
        Withdrawal instance = new Withdrawal(amount, date);
        String expected = "Withdrawal of " + amount.negate().toString() + " on "
                + date;
        String actual = instance.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsPositiveWithdrawal() {
        int amountInSubunits = RANDOM.nextInt(65536) + 1;
        Currency currency = chooseCurrency(
                (cur) -> !cur.getCurrencyCode().equals(cur.getSymbol())
        );
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        String message = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") is not a valid withdrawal";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Withdrawal badInstance = new Withdrawal(amount,
                    LocalDateTime.now());
            System.out.println(message + ", should not have created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsWithdrawalOfZero() {
        Currency currency = chooseCurrency(
                (cur) -> !cur.getCurrencyCode().equals(cur.getSymbol())
        );
        CurrencyAmount zero = new CurrencyAmount(0L, currency);
        String message = "Amount " + zero + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") is  not a valid withdrawal";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Withdrawal badInstance = new Withdrawal(zero, LocalDateTime.now());
            System.out.println(message + ", should not have created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullAmount() {
        LocalDateTime date = LocalDateTime.now().plusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null amount";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Withdrawal badInstance = new Withdrawal(null, date);
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
        int amountInSubunits = RANDOM.nextInt(10000);
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        String message = "Constructor should reject amount " + amount + " of "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") with null date";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Withdrawal badInstance = new Withdrawal(amount, null);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
