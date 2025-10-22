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

    private static class EntityImpl extends Entity {

        EntityImpl(String name, TaxpayerIdentificationNumber tin) {
            super(name, tin);
        }

    }

}
