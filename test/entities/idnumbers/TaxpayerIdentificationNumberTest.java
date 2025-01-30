package entities.idnumbers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaxpayerIdentificationNumberTest {

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