package currency;

import java.util.Currency;

public class CurrencyPair {

    private final Currency source;

    public Currency getFromCurrency() {
        return this.source;
    }

    // TODO: Write tests for this
    public Currency getToCurrency() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public CurrencyPair flip() {
        return this;
    }

    public CurrencyPair(Currency from, Currency to) {
        if (from == null || to == null) {
            String excMsg = "From and To currencies must not be null";
            throw new NullPointerException(excMsg);
        }
        this.source = from;
    }

}
