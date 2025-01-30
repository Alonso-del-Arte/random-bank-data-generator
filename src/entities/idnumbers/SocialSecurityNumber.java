package entities.idnumbers;

import java.io.Serial;
import java.text.DecimalFormat;

public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192962L;

    // TODO: Write a test for this
    public static final int UPPER_NUMBER_LIMIT = -1;

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
        if (number < 0) {
            String excMsg = "Number " + number + " should not be negative";
            throw new IllegalArgumentException(excMsg);
        }
    }

}
