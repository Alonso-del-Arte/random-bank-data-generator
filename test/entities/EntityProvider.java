package entities;

import entities.idnumbers.SocialSecurityNumber;
import static entities.PersonTest.makeSSN;

public class EntityProvider {

    // TODO: Write tests for this
    public static Entity makeEntity() {
        return new Person("John Q. Public", makeSSN());
    }

}
