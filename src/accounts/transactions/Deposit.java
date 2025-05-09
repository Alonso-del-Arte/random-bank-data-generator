package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Deposit extends Transaction {

    private final CurrencyAmount amt;

    // TODO: Write tests for this
    @Override
    public CurrencyAmount getAmount() {
        return this.amt;
    }

    // TODO: Write tests for this
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.now().minusYears(500);
    }

    public Deposit(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
        this.amt = amount;
    }

}
