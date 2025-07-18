package accounts;

import accounts.transactions.Transaction;
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

    ArrayList<Transaction> accountHistory;

    Entity accountBeneficiary;
    
}
