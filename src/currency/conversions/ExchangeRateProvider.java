package currency.conversions;

import currency.CurrencyPair;

import java.util.Currency;

public interface ExchangeRateProvider {

    /**
     * Gives the rate to convert one unit of the source currency to the target
     * currency.
     * @param source The source to convert from. For example, United States
     * dollars (USD).
     * @param target The target to convert one unit of {@code source} to. For
     * example, euros (EUR).
     * @return The conversion rate. In the example as of August 12, 2024, this
     * was 0.915796.
     * @throws RuntimeException If some kind of {@code IOException} or other
     * checked exception occurs, it will be wrapped into an unchecked exception.
     */
    double getRate(Currency source, Currency target);

    default double getRate(CurrencyPair currencies) {
        return -0.0;
    }

}
