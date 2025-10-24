package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    private final String moniker;

    private final EmployerIdentificationNumber num;

    @Override
    public String getName() {
        return this.moniker;
    }

    @Override
    public EmployerIdentificationNumber getTIN() {
        return this.num;
    }

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
        this.moniker = name;
        this.num = ein;
    }

}
