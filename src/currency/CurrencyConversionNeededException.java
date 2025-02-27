package currency;

import java.io.Serial;
import java.util.Currency;

public class CurrencyConversionNeededException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 70580819529728L;

    private final CurrencyAmount amtA, amtB;

    public CurrencyAmount getAmountA() {
        return this.amtA;
    }

    public CurrencyAmount getAmountB() {
        return this.amtB;
    }

    public Currency getCurrencyA() {
        return this.amtA.getCurrency();
    }

    public Currency getCurrencyB() {
        return this.amtB.getCurrency();
    }

    private static int subunitMultiplier(int fractionDigits) {
        int multiplier = 1;
        while (fractionDigits > 0) {
            multiplier *= 10;
            fractionDigits--;
        }
        return multiplier;
    }

    private static String makeMsgForSecondAuxConstructor(CurrencyAmount amt,
                                                        Currency currency) {
        return "Conversion needed for operation with " + amt.toString()
                + " and " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode() + ")";
    }

    // TODO: Chain this constructor to first auxiliary constructor
    public CurrencyConversionNeededException(CurrencyAmount amount,
                                             Currency currency) {
        super(makeMsgForSecondAuxConstructor(amount, currency));
        if (amount == null || currency == null) {
            String excMsg = "Amount, currency should not be null";
            throw new NullPointerException(excMsg);
        }
        this.amtA = amount;
        int amountInSubunits
                = subunitMultiplier(currency.getDefaultFractionDigits());
        this.amtB = new CurrencyAmount(amountInSubunits, currency);
    }

    private static String makeMsgForFirstAuxConstructor(CurrencyAmount amtA,
                                                        CurrencyAmount amtB) {
        return "Conversion needed for operation with " + amtA.toString()
                + " and " + amtB.toString();
    }

    // TODO: Chain this constructor to primary constructor
    public CurrencyConversionNeededException(CurrencyAmount amountA,
                                             CurrencyAmount amountB) {
        super(makeMsgForFirstAuxConstructor(amountA, amountB));
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
