package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class Deposit extends Transaction {

    // TODO: Write tests for this
    @Override
    public CurrencyAmount getAmount() {
        return new CurrencyAmount(100, Currency.getInstance("XCD"));
    }

    // TODO: Write tests for this
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.now().minusYears(500);
    }

    public Deposit(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
    }

}
