package entities;

import entities.idnumbers.TaxpayerIdentificationNumber;

public abstract class Entity {

    private String designation;

    private final TaxpayerIdentificationNumber payerID;

    public String getName() {
        return this.designation;
    }

    public TaxpayerIdentificationNumber getTIN() {
        return this.payerID;
    }

    // TODO: Write tests for this
    public Entity(String name, TaxpayerIdentificationNumber tin) {
        this.designation = name;
        this.payerID = tin;
    }

}
