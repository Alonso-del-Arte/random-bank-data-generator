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
