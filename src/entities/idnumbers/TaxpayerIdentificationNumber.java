package entities.idnumbers;

import java.io.Serial;
import java.io.Serializable;

public abstract class TaxpayerIdentificationNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 17808292273192960L;

    /**
     * One more than the maximum possible Employer Identification Number (EIN)
     * or Social Security Number (SSN).
     */
    public static final int UPPER_NUMBER_LIMIT = 1000000000;

    final int num;

    /**
     * Provides an offset for the hash code function. The idea here is that a
     * subclass will provide the same offset for all its instances, to separate
     * its instances' hash codes from the hash codes for a different subclass.
     * However, a more nuanced approach may be workable.
     * @return A number, preferably not 0, and hopefully not the same as other
     * subclasses.
     */
    abstract int hashCodeOffset();

    // TODO: Write tests for this
    int hashCodeObscurant() {
        return this.num;
    }

    // TODO: Write tests for this
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return Integer.MIN_VALUE;
    }

    TaxpayerIdentificationNumber(int number) {
        if (number < 0 || number >= UPPER_NUMBER_LIMIT) {
            String excMsg = "Number " + number + " is outside of the range 0 to "
                    + (UPPER_NUMBER_LIMIT - 1);
            throw new IllegalArgumentException(excMsg);
        }
        this.num = number;
    }

}
