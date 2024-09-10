package entities.idnumbers;

import static currency.CurrencyAmountTest.RANDOM;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import textops.TextCalculator;

class EmployerIdentificationNumberTest {

    @Test
    void testCorrectEINDashPlacement() {
        System.out.println("correctEINDashPlacement");
        String intermediate = TextCalculator.leftPad(
                Integer.toString(RANDOM.nextInt(1000000000)), 9, '0');
        String s = intermediate.substring(0, 2) + '-'
                + intermediate.substring(2);
        String msg = "EIN " + s
                + " should be found to have correct dash placement";
        assert EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }

}
