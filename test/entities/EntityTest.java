package entities;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import entities.idnumbers.TaxpayerIdentificationNumber;
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

    private class EntityImpl extends Entity {

        EntityImpl(String name, TaxpayerIdentificationNumber tin) {
            super(name, tin);
        }

    }

}
