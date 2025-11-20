package entities.idnumbers;

import static currency.CurrencyAmountTest.*;

import java.io.Serial;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TaxpayerIdentificationNumberTest {

    public static final Random RANDOM
            = new Random(System.currentTimeMillis() << 3);

    static int chooseNumOtherThan(int other) {
        int choice = other;
        while (choice == other) {
            choice = RANDOM.nextInt(TaxpayerIdentificationNumber
                    .UPPER_NUMBER_LIMIT);
        }
        return choice;
    }

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = TaxpayerIdentificationNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

    public static TaxpayerIdentificationNumber makeTIN() {
        return new TaxpayerIdentificationNumberImpl(RANDOM
                .nextInt(TaxpayerIdentificationNumber.UPPER_NUMBER_LIMIT));
    }

    @Test
    void testReferentialEquality() {
        TaxpayerIdentificationNumber instance = makeTIN();
        Object obj = passThrough(instance);
        String message = "TIN " + instance + " should be equal to itself";
        assertEquals(instance, obj, message);
    }

    @Test
    void testNotEqualsNull() {
        TaxpayerIdentificationNumber instance = makeTIN();
        Object obj = provideNull();
        String message = "TIN " + instance + " should not equal null";
        assertNotEquals(instance, obj, message);
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

        @Serial
        private static final long serialVersionUID = 17808292273192961L;

        @Override
        int hashCodeOffset() {
            return 38416;
        }

        TaxpayerIdentificationNumberImpl(int number) {
            super(number);
        }

    }

}