package accounts;

import accounts.transactions.Deposit;
import accounts.transactions.Transaction;
import accounts.transactions.Withdrawal;
import currency.CurrencyAmount;
import entities.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    Entity primaryAccountHolder, secondaryAccountHolder;

    boolean noSecondaryAccountHolderFlag;

    long accountNumber;

    String accountLabel;

    CurrencyAmount accountBalance;

    List<Transaction> accountHistory;

    Entity accountBeneficiary;

    // TODO: Write tests for this
    public void setAccountBeneficiary(Entity entity) {
    }

    // TODO: Write tests for this
    public Entity getAccountBeneficiary() {
        return null;
    }

    // TODO: Write tests for this
    public void removeAccountBeneficiary() {
    }

    // TODO: Write tests for this
    CurrencyAmount getAccountBalance() {
        return new CurrencyAmount(100, java.util.Currency.getInstance("XCD"));
    }

    // TODO: Write tests for this
    public void processDeposit(Deposit deposit) {
    }

    // TODO: Write tests for this
    public void processWithdrawal(Withdrawal withdrawal) {
    }

    // TODO: Write tests for this
    public Account(Entity primary, Entity secondary, Deposit initialDeposit) {
        //
    }

}
