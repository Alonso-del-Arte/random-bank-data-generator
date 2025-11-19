package entities.idnumbers;

import static currency.CurrencyAmountTest.*;

import currency.CurrencyAmountTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import textops.TextCalculator;

class EmployerIdentificationNumberTest {

    @Test
    void testUpperNumberLimitConstant() {
        int expected = 1000000000;
        int actual = EmployerIdentificationNumber.UPPER_NUMBER_LIMIT;
        assertEquals(expected, actual);
    }

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
                Integer.toString(RANDOM.nextInt(1000000000)), 9, '0'
        );
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

    @Test
    void testIncorrectEINDashPlacement() {
        String dashlessNumber = TextCalculator.leftPad(
                Integer.toString(RANDOM.nextInt(1000000000)), 9, '0'
        );
        for (int place = 3; place < 9; place++) {
            String s = dashlessNumber.substring(0, place) + '-'
                    + dashlessNumber.substring(place);
            String msg = "Badly formed EIN " + s
                    + " should be found to have incorrect dash placement";
            assert !EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
        }
    }

    @Test
    void testIncorrectEINDashPlacementPositionLast() {
        String intermediate = TextCalculator.leftPad(
                Integer.toString(RANDOM.nextInt(1000000000)), 9, '0'
        );
        String s = intermediate + '-';
        String msg = "Badly formed EIN " + s
                + " should be found to have incorrect dash placement";
        assert !EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }

    @Test
    void testToString() {
        System.out.println("toString");
        int number = RANDOM.nextInt(1000000000);
        EmployerIdentificationNumber instance = new EmployerIdentificationNumber(number);
        String s = TextCalculator.leftPad(Integer.toString(number), 9, '0');
        String expected = s.substring(0, 2) + '-' + s.substring(2);
        String actual = instance.toString();
        assertEquals(expected, actual);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testReferentialEquality() {
        int num = RANDOM.nextInt(EmployerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber instance
                = new EmployerIdentificationNumber(num);
        Object obj = passThrough(instance);
        String msg = instance + " should be equal to itself";
        assert instance.equals(obj) : msg;
    }

    @Test
    void testNotEqualsNull() {
        int num = RANDOM.nextInt(TaxpayerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber instance
                = new EmployerIdentificationNumber(num);
        String msg = instance + " should not equal null";
        Object obj = CurrencyAmountTest.provideNull();
        assert !instance.equals(obj) : msg;
    }

    @Test
    void testNotEqualsDiffClass() {
        int num = RANDOM.nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT);
        SocialSecurityNumber ssn = new SocialSecurityNumber(num);
        EmployerIdentificationNumber ein
                = new EmployerIdentificationNumber(num);
        String msg = ssn.toRedactedString() + " should not equal EIN " + ein;
        Object obj = CurrencyAmountTest.passThrough(ssn);
        assertNotEquals(ein, obj, msg);
    }

    @Test
    void testNotEqualsDiffNum() {
        int numA = RANDOM.nextInt(EmployerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber einA
                = new EmployerIdentificationNumber(numA);
        int numB = TaxpayerIdentificationNumberTest.chooseNumOtherThan(numA);
        EmployerIdentificationNumber einB
                = new EmployerIdentificationNumber(numB);
        String message = einA + " should not equal " + einB;
        assertNotEquals(einA, einB, message);
    }

    @Test
    void testEquals() {
        System.out.println("equals");
        int num = RANDOM.nextInt(EmployerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber someNum
                = new EmployerIdentificationNumber(num);
        EmployerIdentificationNumber sameNum
                = new EmployerIdentificationNumber(num);
        assertEquals(someNum, sameNum);
    }

    @Test
    void testHashCodeOffset() {
        System.out.println("hashCodeOffset");
        int num = RANDOM.nextInt(EmployerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber instance
                = new EmployerIdentificationNumber(num);
        int actual = instance.hashCodeOffset();
        String msg = "Hash code offset should not be 0";
        assert actual != 0 : msg;
    }

    @Test
    void testHashCodeOffsetShouldNotBePowerOfTwo() {
        int num = RANDOM.nextInt(EmployerIdentificationNumber
                .UPPER_NUMBER_LIMIT);
        EmployerIdentificationNumber instance
                = new EmployerIdentificationNumber(num);
        int actual = instance.hashCodeOffset();
        String msg = "Hash code offset " + actual
                + " should not be power of two";
        assert actual != Integer.highestOneBit(actual) : msg;
    }

    @Test
    void testConstructorRejectsNegativeNumbers() {
        int badNum = RANDOM.nextInt() | Integer.MIN_VALUE;
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            EmployerIdentificationNumber badInstance
                    = new EmployerIdentificationNumber(badNum);
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
        int badNum = EmployerIdentificationNumber.UPPER_NUMBER_LIMIT
                + RANDOM.nextInt(Short.MAX_VALUE);
        String message = "Constructor should reject number " + badNum;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            EmployerIdentificationNumber badInstance
                    = new EmployerIdentificationNumber(badNum);
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
