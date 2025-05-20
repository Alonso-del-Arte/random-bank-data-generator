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
