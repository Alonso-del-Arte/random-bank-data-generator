package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
    }

}
