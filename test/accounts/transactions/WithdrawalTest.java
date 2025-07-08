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

class WithdrawalTest {

    private static Withdrawal makeWithdrawal() {
        int amountInSubunits = -RANDOM.nextInt(262144) - 16;
        Currency currency = chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime dateTime = LocalDateTime.now()
                .minusHours(RANDOM.nextInt(72));
        return new Withdrawal(amount, dateTime);
    }

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
                .minusHours(RANDOM.nextInt(72)).plusMinutes(1);
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
    void testReferentialEquality() {
        Withdrawal instance = makeWithdrawal();
        Object obj = CommentTest.passThrough(instance);
        assertEquals(instance, obj);
    }

    @Test
    void testNotEqualsNull() {
        Withdrawal instance = makeWithdrawal();
        Object obj = CommentTest.provideNull();
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffClass() {
        Withdrawal instance = makeWithdrawal();
        Object obj = CommentTest.passThrough(this);
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffAmount() {
        int centsAmountA = -RANDOM.nextInt(131072) - 8;
        int centsAmountB = 2 * centsAmountA - 1;
        Currency currency = chooseCurrency(2);
        CurrencyAmount amountA = new CurrencyAmount(centsAmountA, currency);
        CurrencyAmount amountB = new CurrencyAmount(centsAmountB, currency);
        LocalDateTime date = LocalDateTime.now().minusDays(RANDOM.nextInt(16));
        Withdrawal withdrawalA = new Withdrawal(amountA, date);
        Withdrawal withdrawalB = new Withdrawal(amountB, date);
        assertNotEquals(withdrawalA, withdrawalB);
    }

    @Test
    void testEquals() {
        System.out.println("equals");
        int millesAmount = -RANDOM.nextInt(524288) - 2;
        Currency currency = chooseCurrency(3);
        CurrencyAmount amount = new CurrencyAmount(millesAmount, currency);
        LocalDateTime date = LocalDateTime.now();
        Withdrawal someWithdrawal = new Withdrawal(amount, date);
        Withdrawal sameWithdrawal = new Withdrawal(amount, date);
        assertEquals(someWithdrawal, sameWithdrawal);
    }

    @Test
    void testNotEqualsDiffDate() {
        int unitsAmount = -RANDOM.nextInt(131072) - 8;
        Currency currency = chooseCurrency(0);
        CurrencyAmount amount = new CurrencyAmount(unitsAmount, currency);
        LocalDateTime dateA = LocalDateTime.now().minusDays(RANDOM.nextInt(16));
        LocalDateTime dateB = dateA.plusMinutes(1);
        Withdrawal withdrawalA = new Withdrawal(amount, dateA);
        Withdrawal withdrawalB = new Withdrawal(amount, dateB);
        assertNotEquals(withdrawalA, withdrawalB);
    }

    @Test
    void testHashCode() {
        System.out.println("hashCode");
        int capacity = RANDOM.nextInt(256) + 64;
        Set<Withdrawal> withdrawals = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        for (int i = 0; i < capacity; i++) {
            Withdrawal withdrawal = makeWithdrawal();
            withdrawals.add(withdrawal);
            hashes.add(withdrawal.hashCode());
        }
        int expected = withdrawals.size();
        int actual = hashes.size();
        String message = "Given " + expected
                + " distinct withdrawals, there should be as many hash codes";
        assertEquals(expected, actual, message);
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
