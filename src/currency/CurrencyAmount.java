package currency;

import java.util.Currency;
import java.util.Locale;

public class CurrencyAmount {

    private static final char MINUS_SIGN = '−';

    private static final Currency U_S_DOLLARS = Currency.getInstance(Locale.US);

    private final long totalCents;

    private final Currency currencyID;

    // TODO: Write tests for this
    public long getAmountInCents() {
        return Long.MIN_VALUE;
    }

    // TODO: Write tests for this
    public long getUnitAmount() {
        return Long.MAX_VALUE;
    }

    // TODO: Write tests for this
    public short getChangeAmount() {
        return Short.MIN_VALUE;
    }

    // TODO: Write tests for this
    public Currency getCurrency() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public CurrencyAmount plus(CurrencyAmount addend) {
        return this;
    }

    // TODO: Write tests for this
    public CurrencyAmount negate() {
        return this;
    }

    // TODO: Write tests for this
    public CurrencyAmount minus(CurrencyAmount subtrahend) {
        return this;
    }

    // TODO: Write tests for this
    public CurrencyAmount times(int multiplicand) {
        return this;
    }

    // TODO: Write tests for this
    public CurrencyAmount divides(int divisor) {
        return this;
    }

    // TODO: Write tests for this
    public static CurrencyAmount parseAmount(String s) {
        return new CurrencyAmount(-1L, Currency.getInstance("XCD"));
    }

    // TODO: Hold off on refactoring these toString() helpers
    private String toStringNotUSDollarsNegative() {
        long absoluteCents = -this.totalCents;
        long withoutCents = absoluteCents / 100;
        String initial = MINUS_SIGN + this.currencyID.getSymbol() + withoutCents
                + '.';
        long centsPart = absoluteCents % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    private String toStringNotUSDollars() {
        if (this.totalCents < 0) {
            return this.toStringNotUSDollarsNegative();
        }
        if (this.totalCents < 10) {
            return this.currencyID.getSymbol() + "0.0" + this.totalCents;
        }
        if (this.totalCents < 100) {
            return this.currencyID.getSymbol() + "0." + this.totalCents;
        }
        long withoutCents = this.totalCents / 100;
        String initial = this.currencyID.getSymbol() + withoutCents + ".";
        long centsPart = this.totalCents % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    private String toStringNegative() {
        long absoluteCents = -this.totalCents;
        long withoutCents = absoluteCents / 100;
        String initial = MINUS_SIGN + "$" + withoutCents + '.';
        long centsPart = absoluteCents % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    public String toString() {
        if (!this.currencyID.equals(U_S_DOLLARS)) {
            return this.toStringNotUSDollars();
        }
        if (this.totalCents < 0) {
            return this.toStringNegative();
        }
        if (this.totalCents < 10) {
            return "$0.0" + this.totalCents;
        }
        if (this.totalCents < 100) {
            return "$0." + this.totalCents;
        }
        long withoutCents = this.totalCents / 100;
        String initial = '$' + Long.toString(withoutCents) + '.';
        long centsPart = this.totalCents % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    // TODO: Override equals(), hashCode()

    // TODO: Make Comparable<CurrencyAmount>, override compareTo()

    // TODO: Write tests for this
    public CurrencyAmount(long centsAmount, Currency currency) {
        if (currency == null) {
            String excMsg = "Currency should not be null";
            throw new NullPointerException(excMsg);
        }
        if (currency.getDefaultFractionDigits() < 0) {
            String excMsg = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode()
                    + ") is not valid for this constructor";
            throw new IllegalArgumentException(excMsg);
        }
        this.totalCents = centsAmount;
        this.currencyID = currency;
    }

}
