package currency.conversions;

import currency.CurrencyPair;

import java.util.Currency;

/**
 * Defines functions for classes that provide access to a currency conversion
 * rate API.
 * @author Alonso del Arte
 */
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

    /**
     * Gives the rate to convert one unit of the source currency to the target
     * currency. The provided default implementation simply unwraps the {@link
     * CurrencyPair} instance and calls the 2-parameter version of the function,
     * which this interface does not implement.
     * @param currencies The pair of currencies, source and target. For example,
     * United States dollars (USD) and euros (EUR).
     * @return The conversion rate. In the example as of January 13, 2025, this
     * was 0.97582065.
     * @throws RuntimeException If some kind of {@code IOException} or other
     * checked exception occurs, it will be wrapped into an unchecked exception.
     * @throws NullPointerException If the {@code currencies} parameter is null.
     */
    default double getRate(CurrencyPair currencies) {
        return this.getRate(currencies.getFromCurrency(),
                currencies.getToCurrency());
    }

}
