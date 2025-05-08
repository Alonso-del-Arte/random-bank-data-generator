package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public abstract class Transaction {

    public abstract CurrencyAmount getAmount();

    public abstract LocalDateTime getTimestamp();

    Transaction(CurrencyAmount amount, LocalDateTime date) {
        if (amount == null) {
            String excMsg = "Amount should not be null";
            throw new NullPointerException(excMsg);
        }
        if (date == null) {
            String excMsg = "Date should not be null";
            throw new NullPointerException(excMsg);
        }
    }

}
