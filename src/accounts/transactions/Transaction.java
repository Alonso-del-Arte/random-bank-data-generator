package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public abstract class Transaction {

    final CurrencyAmount amt;

    final LocalDateTime dateTime;

    public CurrencyAmount getAmount() {
        return this.amt;
    }

    public LocalDateTime getTimestamp() {
        return this.dateTime;
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " of " + this.amt + " on "
                + this.dateTime;
    }

    Transaction(CurrencyAmount amount, LocalDateTime date) {
        if (amount == null || date == null) {
            String excMsg = "Amount, date should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amt = amount;
        this.dateTime = date;
    }

}
