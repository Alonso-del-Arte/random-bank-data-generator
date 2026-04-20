package accounts.transactions.fees;

import accounts.transactions.Comment;
import static accounts.transactions.fees.FeeTest.RANDOM;
import currency.CurrencyAmount;
import currency.CurrencyChooser;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.testframe.api.Asserters.assertInRange;

class MinimumBalanceFeeTest {

    private static final int DEFAULT_ADVISORY_AMOUNT = 10000;

    @Test
    void testGetAmountAuxConstructor() {
        CurrencyAmount expected = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
                expected.getCurrency());
        Fee instance = new MinimumBalanceFee(expected, advisory);
        CurrencyAmount actual = instance.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAmount() {
        System.out.println("getAmount");
        CurrencyAmount expected = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
                amount.getCurrency());
        int days = RANDOM.nextInt(7, 180);
        LocalDateTime expected = LocalDateTime.now().minusDays(days);
        Fee instance = new MinimumBalanceFee(amount, advisory, expected);
        LocalDateTime actual = instance.getTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTimestampAuxConstructor() {
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
                amount.getCurrency());
        LocalDateTime minimum = LocalDateTime.now().minusMinutes(1);
        Fee instance = new MinimumBalanceFee(amount, advisory);
        LocalDateTime maximum = LocalDateTime.now().plusMinutes(1);
        LocalDateTime actual = instance.getTimestamp();
        String msg = "Aux constructor should timestamp the present";
        assertInRange(minimum, actual, maximum, msg);
    }

    @Test
    void testGetAdvisory() {
        System.out.println("getAdvisory");
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        int multiplicand = -RANDOM.nextInt(2, 10);
        CurrencyAmount advisory = amount.times(multiplicand);
        int days = RANDOM.nextInt(7, 180);
        LocalDateTime date = LocalDateTime.now().minusDays(days);
        MinimumBalanceFee instance
                = new MinimumBalanceFee(amount, advisory, date);
        String text = "Minimum amount ought to be at least "
                + advisory.toString();
        Comment expected = new Comment(text, amount.getCurrency(), date);
        Comment actual = instance.getAdvisory();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAdvisoryAuxConstructor() {
        CurrencyAmount amount = FeeTest.makeFeeAmount();
        int multiplicand = -RANDOM.nextInt(2, 10);
        CurrencyAmount advisory = amount.times(multiplicand);
        MinimumBalanceFee instance = new MinimumBalanceFee(amount, advisory);
        Comment actual = instance.getAdvisory();
        System.out.println("advisory = " + advisory);
        String expText = "Minimum amount ought to be at least "
                + advisory.toString();
        String actText = actual.getText();
        assertEquals(expText, actText);
        Currency expCurrency = advisory.getCurrency();
        Currency actCurrency = actual.getAmount().getCurrency();
        assertEquals(expCurrency, actCurrency);
        LocalDateTime expTimestamp = instance.getTimestamp();
        LocalDateTime actTimestamp = actual.getTimestamp();
        assertEquals(expTimestamp, actTimestamp);
    }

    @Test
    void testAuxConstructorRejectsPositiveAmount() {
        CurrencyAmount amount = FeeTest.makeFeeAmount().negate();
        String amtStr = amount.toString();
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
        CurrencyAmount advisory = new CurrencyAmount(DEFAULT_ADVISORY_AMOUNT,
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
