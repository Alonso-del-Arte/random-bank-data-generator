package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    private final CurrencyAmount amt;

    @Override
    public CurrencyAmount getAmount() {
        return this.amt;
    }

    // TODO: Write tests for this
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.now().minusYears(500);
    }

    public Withdrawal(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
        if (amount.isNotNegative()) {
            String excMsg = "Amount " + amount
                    + " is not valid for a withdrawal";
            throw new IllegalArgumentException(excMsg);
        }
        this.amt = amount;
    }

}
