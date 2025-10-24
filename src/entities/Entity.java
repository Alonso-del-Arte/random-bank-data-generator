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

    public Entity(String name, TaxpayerIdentificationNumber tin) {
        if (name == null || tin == null) {
            String excMsg = "Name, TIN should not be null";
            throw new NullPointerException(excMsg);
        }
        this.designation = name;
        this.payerID = tin;
    }

}
