package entities.idnumbers;

import java.io.Serial;

public class EmployerIdentificationNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192961L;

    static boolean correctEINDashPlacement(String s) {
        return s.indexOf('-') == 2;
    }

    // TODO: Write tests for this
    public EmployerIdentificationNumber(int number) {
        super(number);
    }

}
