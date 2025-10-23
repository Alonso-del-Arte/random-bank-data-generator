package entities;

import entities.idnumbers.SocialSecurityNumber;

public class Person extends Entity {

    // TODO: Write tests for this
    @Override
    public String getName() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public SocialSecurityNumber getTIN() {
        return null;
    }

    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
    }

}
