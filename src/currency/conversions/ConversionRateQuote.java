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

    public LocalDateTime getDate() {
        return this.fetchDate;
    }

    public ConversionRateQuote invert() {
        CurrencyPair flipped = this.pair.flip();
        double reciprocal = 1.0 / this.conversionRate;
        return new ConversionRateQuote(flipped, reciprocal, this.fetchDate);
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
        ConversionRateQuote other = (ConversionRateQuote) obj;
        if (!this.pair.equals(((ConversionRateQuote) obj).pair)) {
            return false;
        }
        if (this.conversionRate != other.conversionRate) {
            return false;
        }
        return this.fetchDate.equals(other.fetchDate);
    }

    @Override
    public int hashCode() {
        int hash = 7 * this.pair.hashCode();
        hash += (int) (Double.doubleToLongBits(this.conversionRate) >> 32);
        hash *= 3;
        return hash + this.fetchDate.hashCode();
    }

    @Override
    public String toString() {
        return this.pair.toString() + " at " + this.conversionRate + " as of "
                + this.fetchDate.toString();
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
