package currency;

import java.io.Serial;
import java.util.Currency;

public class CurrencyConversionNeededException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 70580819529728L;

    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amount,
                                             Currency currency) {
        //
    }


    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB) {
        //
    }

    // TODO: Write tests for this
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB,
                                             String message) {
        //
    }

}
