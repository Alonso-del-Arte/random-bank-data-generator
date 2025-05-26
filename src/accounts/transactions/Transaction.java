package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public abstract class Transaction {

    private final CurrencyAmount amt;

    public CurrencyAmount getAmount() {
        return this.amt;
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }

    Transaction(CurrencyAmount amount, LocalDateTime date) {
        if (amount == null) {
            String excMsg = "Amount should not be null";
            throw new NullPointerException(excMsg);
        }
        if (date == null) {
            String excMsg = "Date should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amt = amount;
    }

}
