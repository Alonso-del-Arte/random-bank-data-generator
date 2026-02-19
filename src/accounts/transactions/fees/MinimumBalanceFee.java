package accounts.transactions.fees;

import accounts.transactions.Comment;
import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class MinimumBalanceFee extends Fee {

    // TODO: Write tests for this
    @Override
    public CurrencyAmount getAmount() {
        return new CurrencyAmount(0, java.util.Currency.getInstance("XCD"));
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

    public MinimumBalanceFee(CurrencyAmount amount, CurrencyAmount threshold,
                             LocalDateTime date) {
        super(amount, date);
    }

}
