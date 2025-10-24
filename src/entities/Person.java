package entities;

import entities.idnumbers.SocialSecurityNumber;

public class Person extends Entity {

    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
    }

}
