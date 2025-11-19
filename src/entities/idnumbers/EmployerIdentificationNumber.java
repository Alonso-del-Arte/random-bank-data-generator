package entities.idnumbers;

import java.io.Serial;

import textops.TextCalculator;

public class EmployerIdentificationNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192961L;

    /**
     * The number one more than the theoretical maximum Employer Identification
     * Number, 99-9999999. The constructor can use this constant to check its
     * number parameter.
     */
    public static final int UPPER_NUMBER_LIMIT = 1000000000;

    static boolean correctEINDashPlacement(String s) {
        return s.indexOf('-') == 2;
    }

    // TODO: Write tests for this
    @Override
    int hashCodeOffset() {
        return 0;
    }

    // TODO: Write tests for this
    int hashCodeObscurant() {
        return this.num;
    }

    // TODO: Write tests for this
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmployerIdentificationNumber;
    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        String intermediate = TextCalculator.leftPad(Integer.toString(this.num),
                9, '0');
        return intermediate.substring(0, 2) + '-' + intermediate.substring(2);
    }

    public EmployerIdentificationNumber(int number) {
        super(number);
    }

}
