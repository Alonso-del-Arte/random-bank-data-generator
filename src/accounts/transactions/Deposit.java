package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;

public class Deposit extends Transaction {

    public Deposit(CurrencyAmount amount, LocalDateTime date) {
        super(amount, date);
    }

}
