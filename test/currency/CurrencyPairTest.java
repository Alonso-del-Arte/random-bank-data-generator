package currency;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CurrencyPairTest {

    @Test
    public void testConstructorRejectsNullFromCurrency() {
        Currency to = CurrencyChooser.chooseCurrency();
        String msg = "Currency pair with null From currency and "
                + to.getDisplayName() + " (" + to.getCurrencyCode()
                + ") should cause NPE";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            CurrencyPair instance = new CurrencyPair(null, to);
            System.out.println(msg + ", not created instance "
                    + instance.getClass().getName() + '@'
                    + Integer.toHexString(System.identityHashCode(instance)));
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}