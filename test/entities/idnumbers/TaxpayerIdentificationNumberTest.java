package entities.idnumbers;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaxpayerIdentificationNumberTest {

    public static final Random RANDOM
            = new Random(System.currentTimeMillis() << 3);

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = TaxpayerIdentificationNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsNegativeNumbers() {
        int badNum = RANDOM.nextInt() | Integer.MIN_VALUE;
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            TaxpayerIdentificationNumber badInstance
                    = new TaxpayerIdentificationNumberImpl(badNum);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        String numStr = Integer.toString(badNum);
        String containsMsg = "Message should contain rejected number " + numStr;
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsExcessiveNumber() {
        int badNum = TaxpayerIdentificationNumber.UPPER_NUMBER_LIMIT
                + RANDOM.nextInt(Short.MAX_VALUE);
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            TaxpayerIdentificationNumber badInstance
                    = new TaxpayerIdentificationNumberImpl(badNum);
            System.out.println(message + ", not created instance "
                    + badInstance);
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        String numStr = Integer.toString(badNum);
        String containsMsg = "Message should contain rejected number " + numStr;
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
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