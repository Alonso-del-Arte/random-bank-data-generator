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
        return this == obj;
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return (super.hashCode() % 16) << 16;
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
