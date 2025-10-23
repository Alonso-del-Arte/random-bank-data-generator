package entities;

import static entities.EntityTest.RANDOM;
import entities.idnumbers.SocialSecurityNumber;
import entities.idnumbers.TaxpayerIdentificationNumber;

import static entities.idnumbers.TaxpayerIdentificationNumberTest.makeTIN;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PersonTest {

    private static final String[] FIRST_NAMES = {"Thomas", "Richard", "Harry",
            "Sally"};

    private static final int NUMBER_OF_FIRST_NAMES = FIRST_NAMES.length;

    private static final String[] LAST_NAMES = {"Thomason", "Richardson",
            "Harrison", "Sallinen"};

    private static final int NUMBER_OF_LAST_NAMES = LAST_NAMES.length;

    private static String makeName() {
        int firstNameIndex = RANDOM.nextInt(NUMBER_OF_FIRST_NAMES);
        int lastNameIndex = RANDOM.nextInt(NUMBER_OF_LAST_NAMES);
        return FIRST_NAMES[firstNameIndex] + ' ' + LAST_NAMES[lastNameIndex];
    }

    private static SocialSecurityNumber makeSSN() {
        return new SocialSecurityNumber(RANDOM
                .nextInt(SocialSecurityNumber.UPPER_NUMBER_LIMIT));
    }

    @Test
    void testGetName() {
        System.out.println("getName");
        String expected = makeName();
        SocialSecurityNumber ssn = makeSSN();
        Entity instance = new Person(expected, ssn);
        String actual = instance.getName();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTIN() {
        System.out.println("getTIN");
        String name = makeName();
        SocialSecurityNumber expected = makeSSN();
        Entity instance = new Person(name, expected);
        TaxpayerIdentificationNumber actual = instance.getTIN();
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsNullName() {
        SocialSecurityNumber ssn = makeSSN();
        String message = "Null name for SSN " + ssn + " should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Entity badEntity = new Person(null, ssn);
            System.out.println(message + ", not created instance "
                    + Person.class.getName() + "@"
                    + Integer.toHexString(badEntity.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
