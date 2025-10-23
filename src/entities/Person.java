package entities;

import entities.idnumbers.SocialSecurityNumber;

public class Person extends Entity {

    private final String moniker;

    // TODO: Write tests for this
    @Override
    public String getName() {
        return this.moniker;
    }

    // TODO: Write tests for this
    @Override
    public SocialSecurityNumber getTIN() {
        return null;
    }

    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
        this.moniker = name;
    }

}
