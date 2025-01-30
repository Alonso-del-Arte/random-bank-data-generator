package entities.idnumbers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaxpayerIdentificationNumberTest {

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = TaxpayerIdentificationNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

    private static class TaxpayerIdentificationNumberImpl
            extends TaxpayerIdentificationNumber {

        @Override
        int hashCodeOffset() {
            return 16384;
        }

        TaxpayerIdentificationNumberImpl(int number) {
            super(number);
        }

    }

}