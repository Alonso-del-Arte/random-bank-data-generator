package entities;

import entities.idnumbers.TaxpayerIdentificationNumber;

public abstract class Entity {

    private String designation;

    public String getName() {
        return this.designation;
    }

    // TODO: Write tests for this
    public TaxpayerIdentificationNumber getTIN() {
        return null;
    }

    // TODO: Write tests for this
    public Entity(String name, TaxpayerIdentificationNumber tin) {
        this.designation = name;
    }

}
