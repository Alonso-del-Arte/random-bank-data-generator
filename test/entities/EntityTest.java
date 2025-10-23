package entities;

import entities.idnumbers.TaxpayerIdentificationNumber;
import static entities.idnumbers.TaxpayerIdentificationNumberTest.makeTIN;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EntityTest {

    public static final Random RANDOM = new Random();

    private static String makeName() {
        int len = RANDOM.nextInt(3, 12);
        char[] characters = new char[len];
        for (int i = 0; i < len; i++) {
            characters[i] = (char) RANDOM.nextInt('a', 123);
        }
        return new String(characters);
    }

    @Test
    void testGetName() {
        System.out.println("getName");
        String expected = makeName();
        TaxpayerIdentificationNumber tin = makeTIN();
        Entity instance = new EntityImpl(expected, tin);
        String actual = instance.getName();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTIN() {
        System.out.println("getTIN");
        String name = makeName();
        TaxpayerIdentificationNumber expected = makeTIN();
        Entity instance = new EntityImpl(name, expected);
        TaxpayerIdentificationNumber actual = instance.getTIN();
        assertEquals(expected, actual);
    }

    @Test
    void testConstructorRejectsNullName() {
        TaxpayerIdentificationNumber tin = makeTIN();
        String message = "Null name for TIN " + tin + " should cause exception";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Entity badEntity = new EntityImpl(null, tin);
            System.out.println(message + ", not created instance "
                    + Entity.class.getName() + "@"
                    + Integer.toHexString(badEntity.hashCode()));
        }, message);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    // TODO: Write test constructor rejects null TIN

    private static class EntityImpl extends Entity {

        EntityImpl(String name, TaxpayerIdentificationNumber tin) {
            super(name, tin);
        }

    }

}
