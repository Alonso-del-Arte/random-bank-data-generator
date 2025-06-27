package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Random;

import static currency.CurrencyChooser.chooseCurrency;
import static currency.CurrencyChooser.chooseCurrencyOtherThan;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TransactionTest {

    static final Random RANDOM = new Random(~(System.currentTimeMillis() << 3));

    @Test
    void testGetAmount() {
        int subunits = RANDOM.nextInt();
        Currency currency = chooseCurrency();
        CurrencyAmount expected = new CurrencyAmount(subunits, currency);
        Transaction transaction = new TransactionImpl(expected,
                LocalDateTime.now());
        CurrencyAmount actual = transaction.getAmount();
        String message = "Expecting amount of currency "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode() + ")";
        assertEquals(expected, actual, message);
    }

    private static Object passThrough(Object obj) {
        return obj;
    }

    @Test
    void testReferentialEquality() {
        Currency currency = chooseCurrency();
        int amountInSubunits = RANDOM.nextInt(10000);
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        Transaction instance = new TransactionImpl(amount, date);
        Object obj = passThrough(instance);
        assertEquals(instance, obj);
    }

    @Test
    void testNotEqualsNull() {
        Currency currency = chooseCurrency();
        int amountInSubunits = RANDOM.nextInt(10000);
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        Transaction instance = new TransactionImpl(amount, date);
        Object obj = passThrough(null);
        assertNotEquals(instance, obj);
    }

    @Test
    void testNotEqualsDiffClass() {
        Currency currency = chooseCurrency();
        int amountInSubunits = RANDOM.nextInt(10000);
        CurrencyAmount amount = new CurrencyAmount(amountInSubunits, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        Transaction instanceImpl = new TransactionImpl(amount, date);
        Transaction instanceAnon = new Transaction(amount, date) {

            @Override
            public CurrencyAmount getAmount() {
                return super.getAmount();
            }

        };
        String message = "Instance of class "
                + instanceImpl.getClass().getName()
                + " should not equal instance of class "
                + instanceAnon.getClass().getName();
        assertNotEquals(instanceImpl, instanceAnon, message);
    }

    @Test
    void testNotEqualsSameAmountDiffDate() {
        Currency currency = chooseCurrency();
        int subunits = RANDOM.nextInt(10000);
        CurrencyAmount amount = new CurrencyAmount(subunits, currency);
        LocalDateTime dateA = LocalDateTime.now().minusHours(RANDOM.nextInt(24) + 1);
        LocalDateTime dateB = LocalDateTime.now().plusHours(RANDOM.nextInt(24) + 1);
        Transaction trxA = new TransactionImpl(amount, dateA);
        Transaction trxB = new TransactionImpl(amount, dateB);
        String message = "Transaction of " + amount + " in "
                + currency.getDisplayName() + " ("
                + currency.getCurrencyCode()
                + ") should differ from transaction of " + amount
                + " as one took place on " + dateA + " and the other on "
                + dateB;
        assertNotEquals(trxA, trxB, message);
    }

    @Test
    void testNotEqualsDiffAmountOfSameCurrency() {
        Currency currency = chooseCurrency();
        int subunitsA = RANDOM.nextInt(10000);
        int subunitsB = subunitsA + 1;
        CurrencyAmount amountA = new CurrencyAmount(subunitsA, currency);
        CurrencyAmount amountB = new CurrencyAmount(subunitsB, currency);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        Transaction trxA = new TransactionImpl(amountA, date);
        Transaction trxB = new TransactionImpl(amountB, date);
        String message = "Transaction of " + amountA + " in "
                + currency.getDisplayName() + " (" + currency.getCurrencyCode()
                + ") should differ from transaction of " + amountB
                + " even though both took place on " + date;
        assertNotEquals(trxA, trxB, message);
    }

    @Test
    void testNotEqualsSameAmountOfDiffCurrency() {
        Currency currencyA = chooseCurrency();
        Currency currencyB = chooseCurrencyOtherThan(currencyA);
        int subunits = RANDOM.nextInt(10000);
        CurrencyAmount amountA = new CurrencyAmount(subunits, currencyA);
        CurrencyAmount amountB = new CurrencyAmount(subunits, currencyB);
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        Transaction trxA = new TransactionImpl(amountA, date);
        Transaction trxB = new TransactionImpl(amountB, date);
        String message = "Transaction of " + amountA + " in "
                + currencyA.getDisplayName() + " ("
                + currencyA.getCurrencyCode()
                + ") should differ from transaction of " + amountB + " in "
                + currencyB.getDisplayName() + " (" + currencyB.getCurrencyCode()
                + ") even though both took place on " + date;
        assertNotEquals(trxA, trxB, message);
    }

    @Test
    void testConstructorRejectsNullAmount() {
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null amount";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Transaction badInstance = new TransactionImpl(null, date);
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
            Transaction badInstance = new TransactionImpl(amount, null);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    private static class TransactionImpl extends Transaction {

        TransactionImpl(CurrencyAmount amount, LocalDateTime date) {
            super(amount, date);
        }

    }

}
