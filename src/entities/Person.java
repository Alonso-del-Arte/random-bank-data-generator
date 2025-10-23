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
        if (name == null) {
            String excMsg = "Null name is not valid";
            throw new NullPointerException(excMsg);
        }
        this.moniker = name;
        this.socSecNum = ssn;
    }

}
