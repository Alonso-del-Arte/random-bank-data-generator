package currency.conversions;

import currency.CurrencyAmount;
import currency.CurrencyChooser;
import currency.CurrencyPair;

import java.util.Currency;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {

    private static final double TEST_DELTA = 0.001;

    private static final Currency U_S_DOLLARS = Currency.getInstance(Locale.US);

    private static final Currency EAST_CARIBBEAN_DOLLARS
            = Currency.getInstance("XCD");

    @Test
    void testGetProvider() {
        System.out.println("getProvider");
        ExchangeRateProvider expected
                = new ExchangeRateProviderTest.ExchangeRateProviderImpl();
        CurrencyConverter instance = new CurrencyConverter(expected);
        ExchangeRateProvider actual = instance.getProvider();
        assertEquals(expected, actual);
    }

}
