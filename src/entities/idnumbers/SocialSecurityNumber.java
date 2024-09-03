package entities.idnumbers;

import java.text.DecimalFormat;

public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

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
