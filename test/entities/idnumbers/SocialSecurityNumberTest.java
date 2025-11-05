package entities.idnumbers;

import currency.CurrencyAmountTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static textops.TextCalculator.leftPad;

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
    public void testCorrectSSNDashPlacement() {
        System.out.println("correctSSNDashPlacement");
        String lastFour = Integer.toString(10000 + RANDOM.nextInt(10000))
                .substring(1);
        String s = "***-**-" + lastFour;
        String msg = "\"" + s
                + "\" should be found to have correct SSN dash placement";
        assert SocialSecurityNumber.correctSSNDashPlacement(s) : msg;
    }

    @Test
    public void testDoesNotHaveCorrectSSNDashPlacement() {
        String lastFour = Integer.toString(10000 + RANDOM.nextInt(10000))
                .substring(1);
        String s = "**-***-" + lastFour;
        String msg = "\"" + s
                + "\" should not be found to have correct SSN dash placement";
        assert !SocialSecurityNumber.correctSSNDashPlacement(s) : msg;
    }

    @Test
    void testToString() {
        System.out.println("toString");
        int number = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber instance = new SocialSecurityNumber(number);
        String dashless = leftPad(Integer.toString(number), 9, '0');
        String expected = dashless.substring(0, 3) + '-'
                + dashless.substring(3, 5) + '-' + dashless.substring(5);
        String actual = instance.toString();
        String message = "SSN without dashes is " + number;
        assertEquals(expected, actual, message);
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
    void testToRedactedStringSerial0010To0099() {
        int areaAndGroup = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int serialStart = areaAndGroup + 10;
        int serialStop = areaAndGroup + 100;
        for (int number = serialStart; number < serialStop; number++) {
            SocialSecurityNumber instance = new SocialSecurityNumber(number);
            String expected = "***-**-00" + (number % 100);
            String actual = instance.toRedactedString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToRedactedStringSerial0100To0999() {
        int areaAndGroup = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int serialStart = areaAndGroup + 100;
        int serialStop = areaAndGroup + 1000;
        for (int number = serialStart; number < serialStop; number++) {
            SocialSecurityNumber instance = new SocialSecurityNumber(number);
            String expected = "***-**-0" + (number % 1000);
            String actual = instance.toRedactedString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToRedactedString() {
        System.out.println("toRedactedString");
        int areaAndGroup = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int serialStart = areaAndGroup + 1000;
        int serialStop = areaAndGroup + 10000;
        for (int number = serialStart; number < serialStop; number++) {
            SocialSecurityNumber instance = new SocialSecurityNumber(number);
            String expected = "***-**-" + (number % 10000);
            String actual = instance.toRedactedString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testGetLastFour() {
        System.out.println("getLastFour");
        int areaAndGroup = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        short expected = (short) RANDOM.nextInt(SERIAL_MODULUS);
        int number = areaAndGroup + expected;
        SocialSecurityNumber instance = new SocialSecurityNumber(number);
        short actual = instance.getLastFour();
        String message = "Getting last four of " + instance.toRedactedString();
        assertEquals(expected, actual, message);
    }

    private static int chooseDiffSerial(int serial) {
        int prop = serial;
        while (prop == serial) {
            prop = RANDOM.nextInt(SERIAL_MODULUS);
        }
        return prop;
    }

    @Test
    void testDoesNotMatchLastFour() {
        int areaAndGroupA = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int areaAndGroupB = RANDOM.nextInt(AREA_AND_GROUP_MODULUS)
                * SERIAL_MODULUS;
        int serialA = RANDOM.nextInt(SERIAL_MODULUS);
        int serialB = chooseDiffSerial(serialA);
        SocialSecurityNumber ssnA = new SocialSecurityNumber(areaAndGroupA
                + serialA);
        SocialSecurityNumber ssnB = new SocialSecurityNumber(areaAndGroupB
                + serialB);
        String msg = ssnA.toRedactedString() + " should not match last four of "
                + ssnB.toRedactedString();
        assert !ssnA.matchesLastFour(ssnB) : msg;
    }

    @Test
    void testMatchesLastFourWhenAlsoMatchesFirstFive() {
        int number = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber instance = new SocialSecurityNumber(number);
        String msg = "SSN from " + number
                + " should match last four of SSN from same number";
        assert instance.matchesLastFour(instance) : msg;
    }

    private static int chooseDiffAreaAndGroup(int areaAndGroup) {
        int propNum = areaAndGroup;
        while (propNum == areaAndGroup) {
            propNum = RANDOM.nextInt(AREA_AND_GROUP_MODULUS);
        }
        return propNum;
    }

    @Test
    void testMatchesLastFour() {
        System.out.println("matchesLastFour");
        int areaAndGroupA = RANDOM.nextInt(AREA_AND_GROUP_MODULUS);
        int areaAndGroupB = chooseDiffAreaAndGroup(areaAndGroupA);
        int serial = RANDOM.nextInt(SERIAL_MODULUS);
        SocialSecurityNumber numberA = new SocialSecurityNumber(areaAndGroupA
                * SERIAL_MODULUS + serial);
        SocialSecurityNumber numberB = new SocialSecurityNumber(areaAndGroupB
                * SERIAL_MODULUS + serial);
        String msg = "Last four of " + numberA + " should match last four of "
                + numberB;
        assert numberA.matchesLastFour(numberB) : msg;
    }

    @Test
    void testReferentialEquality() {
        int num = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber instance = new SocialSecurityNumber(num);
        String msg = instance.toRedactedString() + " should equal itself";
        assert instance.equals(instance) : msg;
    }

    @Test
    void testNotEqualsNull() {
        int num = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber instance = new SocialSecurityNumber(num);
        String msg = instance.toRedactedString() + " should not equal null";
        Object obj = CurrencyAmountTest.provideNull();
        assert !instance.equals(obj) : msg;
    }

    @Test
    void testNotEqualsDiffClass() {
        int num = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber ssn = new SocialSecurityNumber(num);
        EmployerIdentificationNumber ein = new EmployerIdentificationNumber(num);
        String msg = ssn.toRedactedString() + " should not equal EIN";
        assertNotEquals(ssn, ein, msg);
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
