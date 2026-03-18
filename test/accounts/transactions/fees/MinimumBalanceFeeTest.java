package accounts.transactions.fees;

import static accounts.transactions.fees.FeeTest.RANDOM;

import currency.CurrencyAmount;
import currency.CurrencyChooser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MinimumBalanceFeeTest {

    private static final int DEFAULT_ADVISORY = 10000;

    @Test
    void testGetAmountAuxConstructor() {
        CurrencyAmount expected = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                expected.getCurrency());
        Fee instance = new MinimumBalanceFee(expected, advisory);
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        CurrencyAmount expected = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                expected.getCurrency());
        LocalDateTime now = LocalDateTime.now();
        Fee instance = new MinimumBalanceFee(expected, advisory, now);
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTimestamp() {
        System.out.println("getTimestamp");
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                amount.getCurrency());
        int days = RANDOM.nextInt(7, 180);
        LocalDateTime expected = LocalDateTime.now().minusDays(days);
        Fee instance = new MinimumBalanceFee(amount, advisory, expected);
        LocalDateTime actual = instance.getTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    void testAuxConstructorRejectsPositiveAmount() {
        CurrencyAmount amount = FeeTest.makeFeeAmount().negate();
        String amtStr = amount.toString();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                amount.getCurrency());
        String message = "Constructor should reject fee amount " + amtStr;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(amount, advisory);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should include \"" + amtStr
                + "\"";
        assert excMsg.contains(amtStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsPositiveAmount() {
        CurrencyAmount amount = FeeTest.makeFeeAmount().negate();
        String amtStr = amount.toString();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                amount.getCurrency());
        LocalDateTime now = LocalDateTime.now();
        String message = "Constructor should reject fee amount " + amtStr;
        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(amount, advisory, now);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should include \"" + amtStr
                + "\"";
        assert excMsg.contains(amtStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testAuxConstructorRejectsNullAmount() {
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                CurrencyChooser.chooseCurrency());
        String message = "Constructor should reject advisory " + advisory
                + " with null amount";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(null, advisory);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullAmount() {
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                CurrencyChooser.chooseCurrency());
        LocalDateTime now = LocalDateTime.now();
        String message = "Constructor should reject advisory " + advisory
                + " with null amount";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(null, advisory, now);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testAuxConstructorRejectsNullAdvisory() {
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        String message = "Constructor should reject amount " + amount
                + " with null advisory";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(amount, null);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullAdvisory() {
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        LocalDateTime now = LocalDateTime.now();
        String message = "Constructor should reject amount " + amount
                + " with null advisory";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(amount, null, now);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    void testConstructorRejectsNullDate() {
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY,
                amount.getCurrency());
        String message = "Constructor should reject advisory " + advisory
                + " and amount " + amount + " with null date";
        Throwable t = assertThrows(NullPointerException.class, () -> {
            Fee badInstance = new MinimumBalanceFee(amount, advisory, null);
            int sysHash = System.identityHashCode(badInstance);
            System.out.println(message + ", not created instance "
                    + Integer.toHexString(sysHash));
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

}
