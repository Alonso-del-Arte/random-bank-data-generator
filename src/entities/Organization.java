package entities;

import entities.idnumbers.EmployerIdentificationNumber;

public class Organization extends Entity {

    private final String moniker;

    private final EmployerIdentificationNumber num;

    @Override
    public EmployerIdentificationNumber getTIN() {
        return this.num;
    }

    public Organization(String name, EmployerIdentificationNumber ein) {
        super(name, ein);
        if (name == null || ein == null) {
            String excMsg = "Name, EIN should not be null";
            throw new NullPointerException(excMsg);
        }
        this.moniker = name;
        this.num = ein;
    }

}
