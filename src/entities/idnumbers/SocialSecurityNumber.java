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

    // TODO: Write tests for this
    public SocialSecurityNumber(int number) {
        super(number);
//        if (number < 0) {
//            String excMsg = "Number " + number + " should not be negative";
//            throw new IllegalArgumentException(excMsg);
//        }
//        if (number >= UPPER_NUMBER_LIMIT) {
//            String excMsg = "Number " + number + " is excessive";
//            throw new IllegalArgumentException(excMsg);
//        }
    }

}
