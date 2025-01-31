package entities.idnumbers;

import java.io.Serial;
import java.text.DecimalFormat;

public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192962L;

    /**
     * The number one more than the theoretical maximum Social Security Number,
     * 999-99-9999 (though that will probably never be assigned to anyone in
     * real life). The constructor can use this constant to check its number
     * parameter.
     */
    public static final int UPPER_NUMBER_LIMIT = 1000000000;

    // TODO: Write tests for this
    static boolean correctSSNDashPlacement(String s) {
        return false;
    }

    // TODO: Write tests for this
    @Override
    int hashCodeOffset() {
        return 0;
    }

    public String toRedactedString() {
        int serial = this.num % 10000;
        if (serial < 10) {
            return "***-**-000" + (this.num % 10);
        }
        if (serial < 100) {
            return "***-**-00" + (this.num % 100);
        }
        if (serial < 1000) {
            return "***-**-0" + (this.num % 1000);
        }
        return "***-**-" + (this.num % 10000);
    }

    /**
     * Constructor.
     * @param number The number. For example, 78051120, which corresponds to
     *               078-05-1120, the Social Security Number originally issued
     *               to Hilda Schrader Whitcher, an executive secretary at E. H.
     *               Ferree, a wallet manufacturer.
     * @throws IllegalArgumentException If {@code number} is negative, or if
     * it's equal to or greater than {@link #UPPER_NUMBER_LIMIT}.
     */
    public SocialSecurityNumber(int number) {
        super(number);
    }

}
