package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class Comment /* extends Transaction */ {

    private final String remark;

    private final Currency fundCur;

    public String getText() {
        return this.remark;
    }

//    @Override
    public CurrencyAmount getAmount() {
        return CurrencyAmount.zeroOf(this.fundCur);
    }

    // TODO: Write tests for this
//    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.now().minusYears(500);
    }

    public Comment(String text, Currency currency, LocalDateTime date) {
//        super(amount, date);
        if (text == null) {
            String excMsg = "Text should not be null";
            throw new NullPointerException(excMsg);
        }
        if (currency == null) {
            String excMsg = "Currency should not be null";
            throw new NullPointerException(excMsg);
        }
        if (date == null) {
            String excMsg = "Date should not be null";
            throw new NullPointerException(excMsg);
        }
        this.remark = text;
        this.fundCur = currency;
    }

}
