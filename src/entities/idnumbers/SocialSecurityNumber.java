package entities.idnumbers;

import java.io.Serial;
import java.text.DecimalFormat;

public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192962L;

    // TODO: Write tests for this
    static boolean correctSSNDashPlacement(String s) {
        return false;
    }

    // TODO: Write tests for this
    public SocialSecurityNumber(int number) {
        super(number);
    }

}
