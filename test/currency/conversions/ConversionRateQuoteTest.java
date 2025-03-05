package currency.conversions;

import currency.CurrencyChooser;
import currency.CurrencyPair;
import static currency.conversions.ExchangeRateProviderTest.RANDOM;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConversionRateQuoteTest {

    @Test
    public void testGetCurrencies() {
        System.out.println("getCurrencies");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair expected = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote instance = new ConversionRateQuote(expected, rate,
                date);
        CurrencyPair actual = instance.getCurrencies();
        assertEquals(expected, actual);
    }

}
