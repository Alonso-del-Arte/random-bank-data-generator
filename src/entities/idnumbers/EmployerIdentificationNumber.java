package entities.idnumbers;

import java.io.Serial;

import org.w3c.dom.Text;
import textops.TextCalculator;

public class EmployerIdentificationNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192961L;

    final int num;

    static boolean correctEINDashPlacement(String s) {
        return s.indexOf('-') == 2;
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
