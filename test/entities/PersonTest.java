package entities;

import static entities.EntityTest.RANDOM;
import entities.idnumbers.SocialSecurityNumber;

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

}
