package accounts.transactions;

import currency.CurrencyAmount;

import java.time.LocalDateTime;
import java.util.Currency;

public class Comment extends Transaction {

    private final String remark;

    private final Currency fundCur;

    public String getText() {
        return this.remark;
    }

    @Override
    public CurrencyAmount getAmount() {
        return CurrencyAmount.zeroOf(this.fundCur);
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
        Comment other = (Comment) obj;
        if (!this.remark.equals(other.remark)) {
            return false;
        }
        if (!this.fundCur.equals(other.fundCur)) {
            return false;
        }
        return this.dateTime.equals(other.dateTime);
    }

    @Override
    public int hashCode() {
        int hash = this.remark.hashCode() << 5;
        hash += this.fundCur.hashCode();
        hash <<= 3;
        return hash + this.dateTime.hashCode();
    }

    public Comment(String text, Currency currency, LocalDateTime date) {
        super(CurrencyAmount.zeroOf(currency), date);
        if (text == null) {
            String excMsg = "Text should not be null";
            throw new NullPointerException(excMsg);
        }
        this.remark = text;
        this.fundCur = currency;
    }

}
