package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    // TODO: Write tests for this
    @Override
    public String getName() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public EmployerIdentificationNumber getTIN() {
        return null;
    }

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
    }

}
