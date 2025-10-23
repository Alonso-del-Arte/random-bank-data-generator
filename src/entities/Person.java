package entities;

import entities.idnumbers.SocialSecurityNumber;

public class Person extends Entity {

    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
        if (name == null || ssn == null) {
            String excMsg = "Name, SSN should not be null";
            throw new NullPointerException(excMsg);
        }
    }

}
