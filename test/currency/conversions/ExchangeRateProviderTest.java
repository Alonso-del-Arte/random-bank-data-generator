package currency.conversions;

import currency.CurrencyChooser;
import currency.CurrencyPair;

import java.util.Currency;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ExchangeRateProviderTest {

    private static final Random RANDOM = new Random();

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