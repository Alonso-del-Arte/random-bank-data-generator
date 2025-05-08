package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Random;

import static currency.CurrencyChooser.chooseCurrency;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TransactionTest {

    static final Random RANDOM = new Random(~(System.currentTimeMillis() << 3));

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

        @Override
        public CurrencyAmount getAmount() {
            return new CurrencyAmount(0, Currency.getInstance("USD"));
        }

        @Override
        public LocalDateTime getTimestamp() {
            return LocalDateTime.now();
        }

        TransactionImpl(CurrencyAmount amount, LocalDateTime date) {
            super(amount, date);
        }

    }

}
