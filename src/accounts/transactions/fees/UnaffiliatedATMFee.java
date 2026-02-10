package accounts.transactions.fees;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class UnaffiliatedATMFee extends Fee {

    // TODO: Write tests for this
    @Override
    public CurrencyAmount getAmount() {
        return new CurrencyAmount(0, java.util.Currency.getInstance("XCD"));
    }

    // TODO: Write tests for this
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.of(1900, 1, 1, 0, 3);
    }

    // TODO: Write tests for this
    @Override
    public String toString() {
        return "SORRY, NOT IMPLEMENTED YET!";
    }

    // TODO: Write tests for this
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 3;
    }

    public UnaffiliatedATMFee(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
    }

}
