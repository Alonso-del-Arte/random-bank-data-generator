package entities.idnumbers;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SocialSecurityNumberTest {

    static final Random RANDOM = new Random(~System.currentTimeMillis());

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = SocialSecurityNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsNegativeNumbers() {
        int badNum = -RANDOM.nextInt(1000000000) - 1;
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
