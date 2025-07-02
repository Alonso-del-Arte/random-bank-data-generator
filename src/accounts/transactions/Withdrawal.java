package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

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
        Withdrawal other = (Withdrawal) obj;
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
        return "Withdrawal of " + this.amt.negate().toString() + " on "
                + this.dateTime.toString();
    }

    public Withdrawal(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
        if (amount.isNotNegative()) {
            String excMsg = "Amount " + amount
                    + " is not valid for a withdrawal";
            throw new IllegalArgumentException(excMsg);
        }
        this.amt = amount;
        this.dateTime = date;
    }

}
