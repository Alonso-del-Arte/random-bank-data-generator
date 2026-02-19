package accounts.transactions.fees;

import accounts.transactions.Comment;
import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class MinimumBalanceFee extends Fee {

    // TODO: Delete when refactoring getAmount()
    private final CurrencyAmount tempAmountHolder;

    // TODO: Write tests for this
    @Override
    public CurrencyAmount getAmount() {
        return this.tempAmountHolder;
    }

    // TODO: Write tests for this
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.of(1900, 1, 1, 0, 1);
    }

    // TODO: Write tests for this
    public Comment getAdvisory() {
        return new Comment("SORRY, NOT IMPLEMENTED YET",
                this.getAmount().getCurrency(), this.getTimestamp());
    }

    // TODO: Write tests for this
    @Override
    public String toString() {
        return "SORRY, NOT IMPLEMENTED... YET";
    }

    // TODO: Write tests for this
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 1;
    }

    // TODO: Write tests for this
    public MinimumBalanceFee(CurrencyAmount amount, CurrencyAmount advisory) {
        super(amount, LocalDateTime.of(1970, 1, 1, 12, 0));
        if (amount == null || advisory == null) {
            String excMsg = "Amount, advisory should not be null";
            throw new NullPointerException(excMsg);
        }
        this.tempAmountHolder = amount;
    }

    // TODO: Write tests for this
    public MinimumBalanceFee(CurrencyAmount amount, CurrencyAmount advisory,
                             LocalDateTime date) {
        super(amount, date);
        if (amount == null) {
            String excMsg = "Amount should not be null";
            throw new NullPointerException(excMsg);
        }
        this.tempAmountHolder = amount;
    }

}
