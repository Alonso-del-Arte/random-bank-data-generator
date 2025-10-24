package entities;

import static entities.EntityTest.RANDOM;
import entities.idnumbers.EmployerIdentificationNumber;
import entities.idnumbers.TaxpayerIdentificationNumber;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OrganizationTest {

    private static final String[] SUFFIXES = {"Co.", "Corp.", "PLLC", "LLC",
            "Ltd."};

    private static final int NUMBER_OF_SUFFIXES = SUFFIXES.length;

    private static String makeName() {
        int len = RANDOM.nextInt(3, 8);
        char[] characters = new char[len];
        for (int i = 0; i < len; i++) {
            characters[i] = (char) RANDOM.nextInt('A', 91);
        }
        String acronym = new String(characters);
        int index = RANDOM.nextInt(NUMBER_OF_SUFFIXES);
        return acronym + ' ' + SUFFIXES[index];
    }

    private static EmployerIdentificationNumber makeEIN() {
        return new EmployerIdentificationNumber(RANDOM
                .nextInt(EmployerIdentificationNumber.UPPER_NUMBER_LIMIT));
    }

    @Test
    void testGetName() {
        System.out.println("getName");
        String expected = makeName();
        EmployerIdentificationNumber ein = makeEIN();
        Entity instance = new Organization(expected, ein);
        String actual = instance.getName();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTIN() {
        System.out.println("getTIN");
        String name = makeName();
        EmployerIdentificationNumber expected = makeEIN();
        Entity instance = new Organization(name, expected);
        TaxpayerIdentificationNumber actual = instance.getTIN();
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsNullName() {
        EmployerIdentificationNumber ein = makeEIN();
        String message = "Null name for EIN " + ein + " should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Entity badEntity = new Organization(null, ein);
            System.out.println(message + ", not created instance "
                    + Organization.class.getName() + "@"
                    + Integer.toHexString(badEntity.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullEIN() {
        String name = makeName();
        String message = "Null EIN for name " + name
                + " should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Entity badEntity = new Organization(name, null);
            System.out.println(message + ", not created instance "
                    + Organization.class.getName() + "@"
                    + Integer.toHexString(badEntity.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
