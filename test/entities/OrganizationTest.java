package entities;

import static entities.EntityTest.RANDOM;
import entities.idnumbers.EmployerIdentificationNumber;
import entities.idnumbers.SocialSecurityNumber;
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

}
