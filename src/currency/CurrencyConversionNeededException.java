package currency;

import java.io.Serial;
import java.util.Currency;

public class CurrencyConversionNeededException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 70580819529728L;

    private final CurrencyAmount amtA, amtB;

    // TODO: Write tests for this
    public CurrencyAmount getAmountA() {
        return this.amtA;
    }

    // TODO: Write tests for this
    public CurrencyAmount getAmountB() {
        return this.amtB;
    }

    public Currency getCurrencyA() {
        return this.amtA.getCurrency();
    }

    public Currency getCurrencyB() {
        return this.amtB.getCurrency();
    }

    // TODO: Chain this constructor to first auxiliary constructor
    public CurrencyConversionNeededException(CurrencyAmount amount,
                                             Currency currency) {
        super("SORRY, NOT IMPLEMENTED YET");
        if (amount == null || currency == null) {
            String excMsg = "Amount, currency should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amtA = amount;
        int amountInSubunits = switch (currency.getDefaultFractionDigits()) {
            case 0 -> 1;
            case 2 -> 100;
            default -> 1000;
        };
        this.amtB = new CurrencyAmount(amountInSubunits, currency);
    }


    // TODO: Write tests for this
    // TODO: Chain this constructor to primary constructor
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB) {
        super("SORRY, NOT IMPLEMENTED YET");
        if (amountA == null || amountB == null) {
            String excMsg = "Amount A should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amtA = amountA;
        this.amtB = amountB;
    }

    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB,
                                             String message) {
        super(message);
        if (message == null || amountA == null || amountB == null) {
            String excMsg = "Message, amounts should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amtA = amountA;
        this.amtB = amountB;
    }

}
