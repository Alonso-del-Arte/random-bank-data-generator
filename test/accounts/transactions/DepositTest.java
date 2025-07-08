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

class DepositTest {

    private static Deposit makeDeposit() {
        int amountInSubunits = RANDOM.nextInt(262144) + 16;
        Currency currency = chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime dateTime = LocalDateTime.now()
                .minusHours(RANDOM.nextInt(72));
        return new Deposit(amount, dateTime);
    }

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = RANDOM.nextInt(10000) + 1;
        CurrencyAmount expected = new CurrencyAmount(amountInSubunits, currency);
        Deposit instance = new Deposit(expected, LocalDateTime.now());
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTimestamp() {
        System.out.println("getTimestamp");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = RANDOM.nextInt(10000) + 1;
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime expected = LocalDateTime.now()
                .plusHours(RANDOM.nextInt(72)).minusMinutes(1);
        Deposit instance = new Deposit(amount, expected);
        LocalDateTime actual = instance.getTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        System.out.println("toString");
        Currency currency = chooseCurrency(
                (cur) -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        int amountInSubunits = RANDOM.nextInt(10000) + 1;
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(72));
        Deposit instance = new Deposit(amount, date);
        String expected = "Deposit of " + amount + " on " + date;
        String actual = instance.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testReferentialEquality() {
        Deposit instance = makeDeposit();
        Object obj = CommentTest.passThrough(instance);
        assertEquals(instance, obj);
    }

    @Test
    void testNotEqualsNull() {
        Deposit instance = makeDeposit();
        Object obj = CommentTest.provideNull();
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffClass() {
        Deposit instance = makeDeposit();
        Object obj = CommentTest.passThrough(this);
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffAmount() {
        int centsAmountA = RANDOM.nextInt(131072) + 8;
        int centsAmountB = 2 * centsAmountA + 1;
        Currency currency = chooseCurrency(2);
        CurrencyAmount amountA = new CurrencyAmount(centsAmountA, currency);
        CurrencyAmount amountB = new CurrencyAmount(centsAmountB, currency);
        LocalDateTime date = LocalDateTime.now().minusDays(RANDOM.nextInt(16));
        Deposit depositA = new Deposit(amountA, date);
        Deposit depositB = new Deposit(amountB, date);
        assertNotEquals(depositA, depositB);
    }

    @Test
    void testEquals() {
        System.out.println("equals");
        int millesAmount = RANDOM.nextInt(524288) + 2;
        Currency currency = chooseCurrency(3);
        CurrencyAmount amount = new CurrencyAmount(millesAmount, currency);
        LocalDateTime date = LocalDateTime.now();
        Deposit someDeposit = new Deposit(amount, date);
        Deposit sameDeposit = new Deposit(amount, date);
        assertEquals(someDeposit, sameDeposit);
    }

    @Test
    void testNotEqualsDiffDate() {
        int unitsAmount = RANDOM.nextInt(131072) + 8;
        Currency currency = chooseCurrency(0);
        CurrencyAmount amount = new CurrencyAmount(unitsAmount, currency);
        LocalDateTime dateA = LocalDateTime.now().minusDays(RANDOM.nextInt(16));
        LocalDateTime dateB = dateA.plusMinutes(1);
        Deposit depositA = new Deposit(amount, dateA);
        Deposit depositB = new Deposit(amount, dateB);
        assertNotEquals(depositA, depositB);
    }

    @Test
    void testHashCode() {
        System.out.println("hashCode");
        int capacity = RANDOM.nextInt(256) + 64;
        Set<Deposit> deposits = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        for (int i = 0; i < capacity; i++) {
            Deposit deposit = makeDeposit();
            deposits.add(deposit);
            hashes.add(deposit.hashCode());
        }
        int expected = deposits.size();
        int actual = hashes.size();
        String message = "Given " + expected
                + " distinct deposits, there should be as many hash codes";
        assertEquals(expected, actual, message);
    }

    @Test
    void testConstructorRejectsNegativeDeposit() {
        int amountInSubunits = -RANDOM.nextInt(65536) - 1;
        Currency currency = chooseCurrency(
                (cur) -> !cur.getCurrencyCode().equals(cur.getSymbol())
        );
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        String message = "Amount " + amount + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") is not a valid deposit";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Deposit badInstance = new Deposit(amount, LocalDateTime.now());
            System.out.println(message + ", should not have created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsDepositOfZero() {
        Currency currency = chooseCurrency(
                (cur) -> !cur.getCurrencyCode().equals(cur.getSymbol())
        );
        CurrencyAmount zero = new CurrencyAmount(0L, currency);
        String message = "Amount " + zero + " (" + currency.getDisplayName()
                + ", " + currency.getCurrencyCode()
                + ") is  not a valid deposit";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Deposit badInstance = new Deposit(zero, LocalDateTime.now());
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
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null amount";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Deposit badInstance = new Deposit(null, date);
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
        int amountInSubunits = RANDOM.nextInt(10000) + 1;
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        String message = "Constructor should reject amount " + amount + " of "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") with null date";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Deposit badInstance = new Deposit(amount, null);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
