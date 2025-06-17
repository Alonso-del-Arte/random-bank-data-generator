package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public abstract class Transaction {

    private final CurrencyAmount amt;

    public CurrencyAmount getAmount() {
        return this.amt;
    }

    // TODO: Write tests for this
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Transaction other = (Transaction) obj;
        if (this.amt.getAmountInSubunits() != other.amt.getAmountInSubunits()) {
            return false;
        }
        return this.amt.getCurrency().equals(other.amt.getCurrency());
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
