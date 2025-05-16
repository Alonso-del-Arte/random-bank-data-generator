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

    @Override
    public String toString() {
        return "Comment: \"" + this.remark + "\" " + this.dateTime.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Comment other = (Comment) obj;
        if (!this.remark.equals(other.remark)) {
            return false;
        }
        return this.fundCur.equals(other.fundCur);
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
    }

    public Comment(String text, Currency currency, LocalDateTime date) {
        super(CurrencyAmount.zeroOf(currency), date);
        if (text == null) {
            String excMsg = "Text should not be null";
            throw new NullPointerException(excMsg);
        }
        this.remark = text;
        this.fundCur = currency;
        this.dateTime = date;
    }

}
