package currency;

import java.util.Currency;

public class CurrencyPair {

    private final Currency source, target;

    public Currency getFromCurrency() {
        return this.source;
    }

    public Currency getToCurrency() {
        return this.target;
    }

    // TODO: Write tests for this
    public CurrencyPair flip() {
        return this;
    }

    @Override
    public String toString() {
        return this.source.getCurrencyCode() + '_'
                + this.target.getCurrencyCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public CurrencyPair(Currency from, Currency to) {
        if (from == null || to == null) {
            String excMsg = "From and To currencies must not be null";
            throw new NullPointerException(excMsg);
        }
        this.source = from;
        this.target = to;
    }

}
