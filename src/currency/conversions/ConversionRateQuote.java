package currency.conversions;

import currency.CurrencyPair;

import java.time.LocalDateTime;

public class ConversionRateQuote {

    private final CurrencyPair pair;

    private final double conversionRate;

    private final LocalDateTime fetchDate;

    public CurrencyPair getCurrencies() {
        return this.pair;
    }

    public double getRate() {
        return this.conversionRate;
    }

    // TODO: Write tests for this
    public LocalDateTime getDate() {
        return this.fetchDate;
    }

    // TODO: Write tests for this
    public ConversionRateQuote invert() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        return this.pair.equals(((ConversionRateQuote) obj).pair);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public ConversionRateQuote(CurrencyPair currencies, double rate) {
        if (currencies == null) {
            throw new NullPointerException("Currencies should not be null");
        }
        if (rate == Double.NEGATIVE_INFINITY) {
            String excMsg = "Rate " + rate + " is not valid";
            throw new IllegalArgumentException(excMsg);
        }
        this.pair = currencies;
        this.conversionRate = rate;
        this.fetchDate = LocalDateTime.now();
    }

    // TODO: Write tests for this
    public ConversionRateQuote(CurrencyPair currencies, double rate,
                               LocalDateTime date) {
        if (currencies == null) {
            throw new NullPointerException("Currencies should not be null");
        }
        if (rate == Double.NEGATIVE_INFINITY) {
            String excMsg = "Rate " + rate + " is not valid";
            throw new IllegalArgumentException(excMsg);
        }
        this.pair = currencies;
        this.conversionRate = rate;
        this.fetchDate = date;
    }

}
