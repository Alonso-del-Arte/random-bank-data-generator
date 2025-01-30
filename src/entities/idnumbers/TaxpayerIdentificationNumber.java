package entities.idnumbers;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public abstract class TaxpayerIdentificationNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 17808292273192960L;

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
        return 0;
    }

    // TODO: Write tests for this
    TaxpayerIdentificationNumber(int number) {
        //
    }

}
