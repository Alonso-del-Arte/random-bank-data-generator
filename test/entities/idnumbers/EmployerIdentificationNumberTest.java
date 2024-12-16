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

    @Test
    void testIncorrectEINDashPlacementPositionZero() {
        String intermediate = TextCalculator.leftPad(
                Integer.toString(RANDOM.nextInt(1000000000)), 9, '0');
        String s = '-' + intermediate;
        String msg = "Badly formed EIN " + s
                + " should be found to have incorrect dash placement";
        assert !EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }

    @Test
    void testIncorrectEINDashPlacementPositionOne() {
        String intermediate = TextCalculator.leftPad(
                Integer.toString(RANDOM.nextInt(100000000)), 8, '0');
        String prefix = Integer.toString(RANDOM.nextInt(10)) + '-';
        String s = prefix + intermediate;
        String msg = "Badly formed EIN " + s
                + " should be found to have incorrect dash placement";
        assert !EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }

}
