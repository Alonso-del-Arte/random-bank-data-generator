package accounts.transactions;

import static accounts.transactions.TransactionTest.RANDOM;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DepositTest {

    @Test
    void testConstructorRejectsNullAmount() {
        LocalDateTime date = LocalDateTime.now().minusHours(RANDOM.nextInt(24));
        String message = "Constructor should reject date " + date
                + " with null deposit";
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

}
