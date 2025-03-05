package currency.conversions;

import currency.CurrencyPair;

import java.time.LocalDateTime;

public class ConversionRateQuote {

    // TODO: Write tests for this
    public CurrencyPair getCurrencies() {
        return new CurrencyPair(java.util.Currency.getInstance("XTS"),
                java.util.Currency.getInstance("XTS"));
    }

    // TODO: Write tests for this
    public double getRate() {
        return -1.0;
    }

    // TODO: Write tests for this
    public LocalDateTime getDate() {
        return LocalDateTime.now().minusYears(100);
    }

    // TODO: Write tests for this
    public ConversionRateQuote invert() {
        return this;
    }

    // TODO: Write tests for this
    public ConversionRateQuote(CurrencyPair currencies, double rate) {
        //
    }

    // TODO: Write tests for this
    public ConversionRateQuote(CurrencyPair currencies, double rate,
                               LocalDateTime date) {
        //
    }

}
