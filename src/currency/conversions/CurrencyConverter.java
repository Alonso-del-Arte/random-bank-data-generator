package currency.conversions;

import currency.CurrencyAmount;

import java.util.Currency;

public class CurrencyConverter {

    private final ExchangeRateProvider exchangeRateProvider;

    /**
     * Discloses the rate provider this converter is using.
     * @return The rate provider given to the constructor.
     */
    public ExchangeRateProvider getProvider() {
        return this.exchangeRateProvider;
    }

    // TODO: Write tests for this
    public CurrencyAmount convert(CurrencyAmount source, Currency target) {
        return source;
    }

    // TODO: Write tests for this
    public CurrencyConverter(ExchangeRateProvider rateProvider) {
        this.exchangeRateProvider = rateProvider;
    }

}
