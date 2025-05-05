package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    public Withdrawal(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
        if (amount == null) {
            String excMsg = "Amount should not be null";
            throw new NullPointerException(excMsg);
        }
        if (date == null) {
            String excMsg = "Date should not be null";
            throw new NullPointerException(excMsg);
        }
    }

}
