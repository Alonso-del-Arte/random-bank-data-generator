package currency.conversions;

import currency.CurrencyPair;

import java.time.LocalDateTime;

public class ConversionRateQuote {

    private final CurrencyPair pair;

    private final double conversionRate;

    public CurrencyPair getCurrencies() {
        return this.pair;
    }

    public double getRate() {
        return this.conversionRate;
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
        this.conversionRate = rate;
    }

    // TODO: Write tests for this
    public ConversionRateQuote(CurrencyPair currencies, double rate,
                               LocalDateTime date) {
        this.pair = currencies;
        this.conversionRate = rate;
    }

}
