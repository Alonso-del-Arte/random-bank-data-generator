package accounts.transactions.fees;

import currency.CurrencyAmount;
import currency.CurrencyChooser;

import java.util.Currency;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FeeTest {

    private static final int MAXIMUM_FEE_IN_SUBUNITS = 10000;

    static final Random RANDOM = new Random(~System.currentTimeMillis() << 4);

    CurrencyAmount makeFeeAmount() {
        int subunits = -RANDOM.nextInt(MAXIMUM_FEE_IN_SUBUNITS) - 1;
        Currency currency = CurrencyChooser.chooseCurrency();
        return new CurrencyAmount(subunits, currency);
    }

}
