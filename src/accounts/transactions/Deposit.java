package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Deposit extends Transaction {

    private final CurrencyAmount amt;

    private final LocalDateTime dateTime;

    @Override
    public CurrencyAmount getAmount() {
        return this.amt;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        return "Deposit of " + this.amt.toString() + " on "
                + this.dateTime.toString();
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
        Deposit other = (Deposit) obj;
        if (!this.amt.equals(other.amt)) {
            return false;
        }
        return this.dateTime.equals(other.dateTime);
    }

    @Override
    public int hashCode() {
        int hash = this.amt.hashCode() << 8;
        return hash + this.dateTime.hashCode();
    }

    public Deposit(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
        if (amount.isNotPositive()) {
            String excMsg = "Amount " + amount + " is not valid for a deposit";
            throw new IllegalArgumentException(excMsg);
        }
        this.amt = amount;
        this.dateTime = date;
    }

}
