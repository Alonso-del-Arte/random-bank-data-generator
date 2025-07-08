package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public abstract class Transaction {

    private final CurrencyAmount amt;

    private final LocalDateTime dateTime;

    public CurrencyAmount getAmount() {
        return this.amt;
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
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
        Transaction other = (Transaction) obj;
        if (!this.dateTime.equals(other.dateTime)) {
            return false;
        }
        return this.amt.equals(other.amt);
    }

    @Override
    public int hashCode() {
        int hash = this.amt.hashCode() << 8;
        return hash + this.dateTime.hashCode();
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
        this.dateTime = date;
    }

}
