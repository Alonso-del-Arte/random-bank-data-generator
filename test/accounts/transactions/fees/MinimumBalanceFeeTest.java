package accounts.transactions.fees;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MinimumBalanceFeeTest {

    private static final int DEFAULT_ADVISORY = 10000;

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        CurrencyAmount expected = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                expected.getCurrency());
        LocalDateTime now = LocalDateTime.now();
        Fee instance = new MinimumBalanceFee(expected, advisory, now);
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

}
