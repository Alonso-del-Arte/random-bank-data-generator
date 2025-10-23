package entities;

import entities.idnumbers.SocialSecurityNumber;

public class Person extends Entity {

    private final String moniker;

    private final SocialSecurityNumber socSecNum;

    @Override
    public String getName() {
        return this.moniker;
    }

    @Override
    public SocialSecurityNumber getTIN() {
        return this.socSecNum;
    }

    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
        this.moniker = name;
        this.socSecNum = ssn;
    }

}
