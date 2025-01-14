package currency;

import java.util.Currency;

public class CurrencyPair {

    // TODO: Write tests for this
    public Currency getFromCurrency() {
        return Currency.getInstance("XTS");
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
        // TODO: Write tests for this
    }

}
