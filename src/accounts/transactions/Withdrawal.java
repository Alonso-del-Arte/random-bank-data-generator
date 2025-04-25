package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    public Withdrawal(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
    }

}
