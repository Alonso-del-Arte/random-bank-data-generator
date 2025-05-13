package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class Comment extends Transaction {

    private final String remark;

    private final Currency fundCur;

    private final LocalDateTime dateTime;

    public String getText() {
        return this.remark;
    }

    @Override
    public CurrencyAmount getAmount() {
        return CurrencyAmount.zeroOf(this.fundCur);
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.dateTime;
    }

    public Comment(String text, Currency currency, LocalDateTime date) {
        super(CurrencyAmount.zeroOf(currency), date);
        if (text == null) {
            String excMsg = "Text should not be null";
            throw new NullPointerException(excMsg);
        }
        if (currency == null) {
            String excMsg = "Currency should not be null";
            throw new NullPointerException(excMsg);
        }
        this.remark = text;
        this.fundCur = currency;
        this.dateTime = date;
    }

}
