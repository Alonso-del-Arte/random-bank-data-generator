package entities.idnumbers;

import java.io.Serial;

import textops.TextCalculator;

public class EmployerIdentificationNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192961L;

    final int num;

    static boolean correctEINDashPlacement(String s) {
        return s.indexOf('-') == 2;
    }

    // TODO: Write tests for this
    @Override
    int hashCodeOffset() {
        return 0;
    }

    @Override
    public String toString() {
        String intermediate = TextCalculator.leftPad(Integer.toString(this.num),
                9, '0');
        return intermediate.substring(0, 2) + '-' + intermediate.substring(2);
    }

    // TODO: Write tests for this
    public EmployerIdentificationNumber(int number) {
        super(number);
        this.num = number;
    }

}
