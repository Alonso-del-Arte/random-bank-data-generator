package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
        if (name == null || ein == null) {
            String excMsg = "Name, EIN should not be null";
            throw new NullPointerException(excMsg);
        }
    }

}
