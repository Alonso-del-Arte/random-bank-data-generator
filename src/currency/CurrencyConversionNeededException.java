package currency;

import java.io.Serial;
import java.util.Currency;

public class CurrencyConversionNeededException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 70580819529728L;

    // TODO: Write tests for this
    public CurrencyAmount getAmountA() {
        return new CurrencyAmount(-1, Currency.getInstance("USD"));
    }

    // TODO: Write tests for this
    public CurrencyAmount getAmountB() {
        return new CurrencyAmount(-1, Currency.getInstance("USD"));
    }

    // TODO: Write tests for this
    public Currency getCurrencyA() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public Currency getCurrencyB() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amount,
                                             Currency currency) {
        super("SORRY, NOT IMPLEMENTED YET");
    }


    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB) {
        super("SORRY, NOT IMPLEMENTED YET");
    }

    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB,
                                             String message) {
        super(message);
    }

}
