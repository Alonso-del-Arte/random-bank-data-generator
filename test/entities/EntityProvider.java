package entities;

import entities.idnumbers.SocialSecurityNumber;

public class EntityProvider {

    // TODO: Write tests for this
    public static Entity makeEntity() {
        return new Person("John Q. Public", new SocialSecurityNumber(1));
    }

}
