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
    void testGetCurrencies() {
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
    void testGetCurrenciesFromAuxConstructor() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair expected = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        ConversionRateQuote instance = new ConversionRateQuote(expected, rate);
        CurrencyPair actual = instance.getCurrencies();
        assertEquals(expected, actual);
    }

    @Test
    void testGetRate() {
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
    void testGetRateFromAuxConstructor() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double expected = 0.5 + RANDOM.nextDouble();
        ConversionRateQuote instance = new ConversionRateQuote(currencies,
                expected);
        double actual = instance.getRate();
        assertEquals(expected, actual);
    }

    @Test
    void testGetDate() {
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

    private static Object passThrough(Object object) {
        return object;
    }

    @Test
    void testReferentialEquality() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote quote = new ConversionRateQuote(currencies, rate,
                date);
        Object obj = passThrough(quote);
        String message = "Quote " + quote + " should be equal to itself";
        assertEquals(quote, obj, message);
    }

    private static Object provideNull() {
        return null;
    }

    @Test
    public void testNotEqualsNull() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote quote = new ConversionRateQuote(currencies, rate,
                date);
        Object obj = provideNull();
        String msg = quote + " should not equal null";
        assert !quote.equals(obj) : msg;
    }

    @Test
    public void testNotEqualsDiffClass() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote quote = new ConversionRateQuote(currencies, rate,
                date);
        Object[] objects = {this, from, to, currencies, date};
        String msgPart = quote + " should not equal ";
        for (Object obj : objects) {
            String msg = msgPart + obj.toString();
            assert !quote.equals(obj) : msg;
        }
    }

    @Test
    public void testNotEqualsQuoteForDiffPair() {
        Currency fromA = CurrencyChooser.chooseCurrency();
        Currency toA = CurrencyChooser.chooseCurrencyOtherThan(fromA);
        CurrencyPair pairA = new CurrencyPair(fromA, toA);
        Currency fromB = CurrencyChooser.chooseCurrencyOtherThan(fromA);
        Currency toB = CurrencyChooser.chooseCurrencyOtherThan(toA);
        CurrencyPair pairB = new CurrencyPair(fromB, toB);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote quoteA = new ConversionRateQuote(pairA, rate, date);
        ConversionRateQuote quoteB = new ConversionRateQuote(pairB, rate, date);
        String message = "Quote for " + pairA + " should not equal quote for "
                + pairB;
        assertNotEquals(quoteA, quoteB, message);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote someQuote = new ConversionRateQuote(currencies,
                rate, date);
        ConversionRateQuote sameQuote = new ConversionRateQuote(currencies,
                rate, date);
        String message = "Quote for " + currencies + " at a rate of " + rate
                + " on " + date
                + " should match other quote with those same parameters";
        assertEquals(sameQuote, someQuote, message);
    }

    @Test
    public void testNotEqualsDiffRate() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rateA = 0.5 + RANDOM.nextDouble();
        double rateB = Double.longBitsToDouble(Double.doubleToLongBits(rateA)
                + 1);
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote quoteA = new ConversionRateQuote(currencies, rateA,
                date);
        ConversionRateQuote quoteB = new ConversionRateQuote(currencies, rateB,
                date);
        String message = "Quote for " + currencies + " at a rate of " + rateA
                + " on " + date
                + " should not match quote for same currencies at " + rateB;
        assertNotEquals(quoteA, quoteB, message);
    }

    @Test
    public void testNotEqualsDiffDate() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 0.5 + RANDOM.nextDouble();
        LocalDateTime dateA = LocalDateTime.now();
        LocalDateTime dateB = dateA.minusMinutes(RANDOM.nextInt(60) + 1);
        ConversionRateQuote quoteA = new ConversionRateQuote(currencies, rate,
                dateA);
        ConversionRateQuote quoteB = new ConversionRateQuote(currencies, rate,
                dateB);
        String message = "Quote for " + currencies + " at a rate of "
                + rate + " on " + dateA
                + " should not match quote for same currencies at same rate on "
                + dateB;
        assertNotEquals(quoteA, quoteB, message);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int quartets = RANDOM.nextInt(16) + 4;
        int initialCapacity = 4 * quartets;
        Set<ConversionRateQuote> quotes = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        LocalDateTime date = LocalDateTime.now();
        for (int i = 0; i < initialCapacity; i++) {
            Currency from = CurrencyChooser.chooseCurrency();
            Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
            CurrencyPair currencies = new CurrencyPair(from, to);
            double rate = 0.5 + RANDOM.nextDouble();
            date = date.minusMinutes(i);
            ConversionRateQuote quote1 = new ConversionRateQuote(currencies,
                    rate, date);
            quotes.add(quote1);
            hashes.add(quote1.hashCode());
            ConversionRateQuote quote2
                    = new ConversionRateQuote(currencies.flip(), rate, date);
            quotes.add(quote2);
            hashes.add(quote2.hashCode());
            ConversionRateQuote quote3 = new ConversionRateQuote(currencies,
                    1.0 / rate, date);
            quotes.add(quote3);
            hashes.add(quote3.hashCode());
            ConversionRateQuote quote4 = new ConversionRateQuote(currencies,
                    rate, date.minusHours(i));
            quotes.add(quote4);
            hashes.add(quote4.hashCode());
        }
        int numberOfQuotes = quotes.size();
        int minimum = 3 * numberOfQuotes / 5;
        int actual = hashes.size();
        String msg = "Given " + numberOfQuotes
                + " quotes, there should be at least " + minimum
                + " distinct hash codes, got " + actual + " distinct";
        assert minimum <= actual : msg;
        System.out.println(msg);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 1.0 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now()
                .minusMinutes(RANDOM.nextInt(60));
        ConversionRateQuote instance = new ConversionRateQuote(currencies, rate,
                date);
        String expected = currencies + " at " + rate + " as of " + date;
        String actual = instance.toString();
        assertEquals(actual, expected);
    }

    @Test
    public void testInvert() {
        System.out.println("invert");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 1.0 + RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        ConversionRateQuote instance = new ConversionRateQuote(currencies,
                rate, date);
        CurrencyPair flippedPair = currencies.flip();
        double invertedRate = 1.0 / rate;
        ConversionRateQuote expected = new ConversionRateQuote(flippedPair,
                invertedRate, date);
        ConversionRateQuote actual = instance.invert();
        assertEquals(actual, expected);
    }

    @Test
    public void testAuxConstructorFillsInCurrentDateTime() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = 1.0 + RANDOM.nextDouble();
        ConversionRateQuote instance = new ConversionRateQuote(currencies,
                rate);
        LocalDateTime expected = LocalDateTime.now();
        int minutes = 5;
        LocalDateTime minimum = expected.minusMinutes(minutes);
        LocalDateTime maximum = expected.plusMinutes(minutes);
        LocalDateTime actual = instance.getDate();
        String msg = "Timestamp " + actual + " should be at least " + minimum
                + " and at most " + maximum;
        assert actual.isAfter(minimum) : msg;
        assert actual.isBefore(maximum) : msg;
    }

    @Test
    public void testConstructorRejectsNullCurrencyPair() {
        double rate = RANDOM.nextDouble();
        LocalDateTime date = LocalDateTime.now();
        String message = "Using null currency pair should've caused NPE";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            ConversionRateQuote instance = new ConversionRateQuote(null, rate,
                    date);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testAuxConstructorRejectsNullCurrencyPair() {
        double rate = RANDOM.nextDouble();
        String message = "Using null currency pair should've caused NPE";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            ConversionRateQuote instance = new ConversionRateQuote(null, rate);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testConstructorRejectsNegativeInfinityRate() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = Double.NEGATIVE_INFINITY;
        LocalDateTime date = LocalDateTime.now();
        String message = "Using " + rate
                + " for rate should've caused exception";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            ConversionRateQuote instance = new ConversionRateQuote(currencies,
                    rate, date);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testAuxConstructorRejectsNegativeInfinityRate() {
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        double rate = Double.NEGATIVE_INFINITY;
        String message = "Using " + rate
                + " for rate should've caused exception";
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            ConversionRateQuote instance = new ConversionRateQuote(currencies,
                    rate);
            System.out.println(message + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
