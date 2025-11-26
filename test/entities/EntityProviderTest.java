package entities;

import static entities.EntityTest.RANDOM;
import entities.idnumbers.EmployerIdentificationNumber;
import entities.idnumbers.SocialSecurityNumber;
import entities.idnumbers.TaxpayerIdentificationNumber;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EntityProviderTest {

    @Test
    void testTINsAreDistinct() {
        int capacity = RANDOM.nextInt(32) + 128;
        Set<TaxpayerIdentificationNumber> tins = new HashSet<>(capacity);
        for (int i = 0; i < capacity; i++) {
            Entity entity = EntityProvider.makeEntity();
            tins.add(entity.getTIN());
        }
        int minimum = 3 * capacity / 5;
        int actual = tins.size();
        String msg = "After " + capacity + " calls, there should be at least "
                + minimum + " distinct TINs, only found " + actual;
        assert actual >= minimum : msg;
    }

    // TODO: Write test that names are distinct

    // TODO: Write test that both persons and organizations are given

}
