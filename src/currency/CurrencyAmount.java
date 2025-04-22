package currency;

import java.util.Currency;
import java.util.Locale;

public class CurrencyAmount {

    private static final char MINUS_SIGN = '−';

    private static final Currency U_S_DOLLARS = Currency.getInstance(Locale.US);

    private final long totalSubunits;

    private final Currency currencyID;

    // TODO: Write tests for this
    public long getAmountInSubunits() {
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

    public Currency getCurrency() {
        return this.currencyID;
    }

    public boolean isPositive() {
        return this.totalSubunits > 0L;
    }

    public boolean isNotNegative() {
        return this.totalSubunits > -1L;
    }

    // TODO: Write tests for this
    public boolean isZero() {
        return false;
    }

    // TODO: Write tests for this
    public boolean isNotPositive() {
        return false;
    }

    // TODO: Write tests for this
    public boolean isNegative() {
        return false;
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
    private String toStringCurrencyNoSubdivisionsNegative() {
        return MINUS_SIGN + this.currencyID.getSymbol() + (-this.totalSubunits);
    }

    private String toStringCurrencyNoSubdivisions() {
        if (this.totalSubunits < 0) {
            return this.toStringCurrencyNoSubdivisionsNegative();
        }
        return this.currencyID.getSymbol() + this.totalSubunits;
    }

    private String toStringDivideInTenThousandths() {
        if (this.totalSubunits < 10) {
            return this.currencyID.getSymbol() + "0.000" + this.totalSubunits;
        }
        if (this.totalSubunits < 100) {
            return this.currencyID.getSymbol() + "0.00" + this.totalSubunits;
        }
        if (this.totalSubunits < 1000) {
            return this.currencyID.getSymbol() + "0.0" + this.totalSubunits;
        }
        if (this.totalSubunits < 10000) {
            return this.currencyID.getSymbol() + "0." + this.totalSubunits;
        }
        String numStr = Long.toString(this.totalSubunits);
        int subdivsBegin = numStr.length() - 4;
        return this.currencyID.getSymbol() + numStr.substring(0, subdivsBegin)
                + '.' + numStr.substring(subdivsBegin);
    }

    private String toStringSubdividedInDarahim() {
        if (this.totalSubunits < 10) {
            return this.currencyID.getSymbol() + "0.00" + this.totalSubunits;
        }
        if (this.totalSubunits < 100) {
            return this.currencyID.getSymbol() + "0.0" + this.totalSubunits;
        }
        if (this.totalSubunits < 1000) {
            return this.currencyID.getSymbol() + "0." + this.totalSubunits;
        }
        String numStr = Long.toString(this.totalSubunits);
        int darahimBegin = numStr.length() - 3;
        return this.currencyID.getSymbol() + numStr.substring(0, darahimBegin)
                + '.' + numStr.substring(darahimBegin);
    }

    private String toStringNotUSDollarsNegative() {
        long absoluteCents = -this.totalSubunits;
        long withoutCents = absoluteCents / 100;
        String initial = MINUS_SIGN + this.currencyID.getSymbol() + withoutCents
                + '.';
        long centsPart = absoluteCents % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    private String toStringNotUSDollars() {
        if (this.currencyID.getDefaultFractionDigits() == 0) {
            return this.toStringCurrencyNoSubdivisions();
        }
        if (this.currencyID.getDefaultFractionDigits() == 3) {
            return this.toStringSubdividedInDarahim();
        }
        if (this.currencyID.getDefaultFractionDigits() == 4) {
            return this.toStringDivideInTenThousandths();
        }
        if (this.totalSubunits < 0) {
            return this.toStringNotUSDollarsNegative();
        }
        if (this.totalSubunits < 10) {
            return this.currencyID.getSymbol() + "0.0" + this.totalSubunits;
        }
        if (this.totalSubunits < 100) {
            return this.currencyID.getSymbol() + "0." + this.totalSubunits;
        }
        long withoutCents = this.totalSubunits / 100;
        String initial = this.currencyID.getSymbol() + withoutCents + ".";
        long centsPart = this.totalSubunits % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
    }

    private String toStringNegative() {
        long absoluteCents = -this.totalSubunits;
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
        if (this.totalSubunits < 0) {
            return this.toStringNegative();
        }
        if (this.totalSubunits < 10) {
            return "$0.0" + this.totalSubunits;
        }
        if (this.totalSubunits < 100) {
            return "$0." + this.totalSubunits;
        }
        long withoutCents = this.totalSubunits / 100;
        String initial = '$' + Long.toString(withoutCents) + '.';
        long centsPart = this.totalSubunits % 100;
        String centsStr = (centsPart < 10) ? "0" + centsPart
                : Long.toString(centsPart);
        return initial + centsStr;
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
        CurrencyAmount other = (CurrencyAmount) obj;
        if (this.totalSubunits != other.totalSubunits) {
            return false;
        }
        return this.currencyID.equals(other.currencyID);
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
    }

    // TODO: Make Comparable<CurrencyAmount>, override compareTo()

    // TODO: Write tests for this
    public CurrencyAmount(long amountInSubunits, Currency currency) {
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
        this.totalSubunits = amountInSubunits;
        this.currencyID = currency;
    }

}
