package currency.conversions;

import currency.CurrencyChooser;
import currency.CurrencyPair;
import static currency.conversions.ExchangeRateProviderTest.RANDOM;

import java.time.LocalDateTime;
import java.time.Year;
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

    @Test
    public void testGetCurrenciesFromAuxConstructor() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair expected = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        ConversionRateQuote instance = new ConversionRateQuote(expected, rate);
        CurrencyPair actual = instance.getCurrencies();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRate() {
        System.out.println("getRate");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double expected = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote instance = new ConversionRateQuote(currencies,
                expected, date);
        double actual = instance.getRate();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRateFromAuxConstructor() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double expected = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote instance = new ConversionRateQuote(currencies,
                expected);
        double actual = instance.getRate();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime now = LocalDateTime.now();
        int days = RANDOM.nextInt(now.getMonth().length(Year
                .isLeap(now.getYear())));
        LocalDateTime expected = now.minusDays(days);
        ConversionRateQuote instance = new ConversionRateQuote(currencies, rate,
                expected);
        LocalDateTime actual = instance.getDate();
        assertEquals(expected, actual);
    }

}
