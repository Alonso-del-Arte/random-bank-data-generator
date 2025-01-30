package entities.idnumbers;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SocialSecurityNumberTest {

    static final Random RANDOM = new Random(~System.currentTimeMillis());

    private static final int AREA_AND_GROUP_MODULUS = 100000;

    private static final int SERIAL_MODULUS = 10000;

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = SocialSecurityNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

    @Test
    void testToRedactedStringSerial0000To0009() {
        int areaAndGroup = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int serialStop = areaAndGroup + 10;
        for (int number = areaAndGroup; number < serialStop; number++) {
            SocialSecurityNumber instance = new SocialSecurityNumber(number);
            String expected = "***-**-000" + (number % 10);
            String actual = instance.toRedactedString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testConstructorRejectsNegativeNumbers() {
        int badNum = RANDOM.nextInt() | Integer.MIN_VALUE;
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            SocialSecurityNumber badInstance = new SocialSecurityNumber(badNum);
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
        int badNum = SocialSecurityNumber.UPPER_NUMBER_LIMIT
                + RANDOM.nextInt(Short.MAX_VALUE);
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            SocialSecurityNumber badInstance = new SocialSecurityNumber(badNum);
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

}
