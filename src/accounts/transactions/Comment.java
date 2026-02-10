package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class Comment extends Transaction {

    private final String remark;

    public String getText() {
        return this.remark;
    }

    @Override
    public String toString() {
        return "Comment: \"" + this.remark + "\" " + this.dateTime.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        return this.remark.equals(((Comment) obj).remark);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (this.remark.hashCode() << 5);
    }

    public Comment(String text, Currency currency, LocalDateTime date) {
        super(CurrencyAmount.zeroOf(currency), date);
        if (text == null) {
            String excMsg = "Text should not be null";
            throw new NullPointerException(excMsg);
        }
        this.remark = text;
    }

}
