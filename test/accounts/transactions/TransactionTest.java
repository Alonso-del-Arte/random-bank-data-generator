package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TransactionTest {

    static final Random RANDOM = new Random(~(System.currentTimeMillis() << 3));

    private static class TransactionImpl extends Transaction {

        TransactionImpl(CurrencyAmount amount, LocalDateTime date) {
            super(amount, date);
        }

    }

}
