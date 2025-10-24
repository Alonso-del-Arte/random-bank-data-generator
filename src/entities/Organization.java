package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    private final String moniker;

    @Override
    public String getName() {
        return this.moniker;
    }

    // TODO: Write tests for this
    @Override
    public EmployerIdentificationNumber getTIN() {
        return null;
    }

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
        this.moniker = name;
    }

}
