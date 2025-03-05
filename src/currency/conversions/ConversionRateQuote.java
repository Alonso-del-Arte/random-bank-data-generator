package currency.conversions;

import currency.CurrencyPair;

import java.time.LocalDateTime;

public class ConversionRateQuote {

    private final CurrencyPair pair;

    // TODO: Write tests for this
    public CurrencyPair getCurrencies() {
        return this.pair;
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

    public ConversionRateQuote(CurrencyPair currencies, double rate) {
        this.pair = currencies;
    }

    // TODO: Write tests for this
    public ConversionRateQuote(CurrencyPair currencies, double rate,
                               LocalDateTime date) {
        this.pair = currencies;
    }

}
