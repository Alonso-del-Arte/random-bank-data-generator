package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public abstract class Transaction {

    private final CurrencyAmount amt;

    public CurrencyAmount getAmount() {
        return this.amt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.getClass().equals(obj.getClass());
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
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
