package currency.conversions;

import currency.CurrencyChooser;
import currency.CurrencyPair;

import java.util.Currency;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ExchangeRateProviderTest {

    private static final Random RANDOM = new Random();

    @Test
    public void testGetRate() {
        System.out.println("getRate");
        Currency from = CurrencyChooser.chooseCurrency();
        Currency to = CurrencyChooser.chooseCurrencyOtherThan(from);
        CurrencyPair currencies = new CurrencyPair(from, to);
        ExchangeRateProviderImpl instance = new ExchangeRateProviderImpl();
        double actual = instance.getRate(currencies);
        String callMsg = "1-parameter getRate() should've called 2-parameter";
        assert instance.nonDefaultGetRateCallCount == 1 : callMsg;
        double expected = instance.mostRecentReturn;
        double delta = 0.0001;
        String message = "Inquiring exchange rate from " + from.getDisplayName()
                + " (" + from.getCurrencyCode() + ") to " + to.getDisplayName()
                + " (" + to.getCurrencyCode() + ")";
        assertEquals(expected, actual, delta, message);
        assertEquals(from, instance.mostRecentSource);
        assertEquals(to, instance.mostRecentTarget);
    }

    private static class ExchangeRateProviderImpl
            implements ExchangeRateProvider {

        private int nonDefaultGetRateCallCount = 0;

        private Currency mostRecentSource;

        private Currency mostRecentTarget;

        private double mostRecentReturn = Double.NaN;

        @Override
        public double getRate(Currency source, Currency target) {
            this.nonDefaultGetRateCallCount++;
            this.mostRecentSource = source;
            this.mostRecentTarget = target;
            this.mostRecentReturn = 1.0 + RANDOM.nextDouble();
            return this.mostRecentReturn;
        }

    }

}