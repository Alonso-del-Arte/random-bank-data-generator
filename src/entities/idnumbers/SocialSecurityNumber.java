package entities.idnumbers;

import java.io.Serial;

import static textops.TextCalculator.leftPad;

public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    @Serial
    private static final long serialVersionUID = 17808292273192962L;

    private static final String AREA_AND_GROUP_REDACTION = "***-**-";

    private static final int SERIAL_MODULUS = 10000;

    /**
     * The number one more than the theoretical maximum Social Security Number,
     * 999-99-9999 (though that will probably never be assigned to anyone in
     * real life). The constructor can use this constant to check its number
     * parameter.
     */
    public static final int UPPER_NUMBER_LIMIT = 1000000000;

    static boolean correctSSNDashPlacement(String s) {
        if (s.length() != 11) {
            return false;
        }
        return (s.indexOf('-') == 3) && (s.indexOf('-', 4) == 6)
                && (s.indexOf('-', 7) == -1);
    }

    // TODO: Write tests for this
    @Override
    int hashCodeOffset() {
        return 16381;
    }

    @Override
    int hashCodeObscurant() {
        int hash = 239 * (this.num / 1000000);
        hash += 47 * ((this.num / 10000) % 100);
        hash += 100000 * (this.num % 10000);
        return hash;    }

    public short getLastFour() {
        return (short) (this.num % SERIAL_MODULUS);
    }

    public boolean matchesLastFour(SocialSecurityNumber other) {
        return (this.num % SERIAL_MODULUS) == (other.num % SERIAL_MODULUS);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass().equals(obj.getClass())) {
            return this.num == ((SocialSecurityNumber) obj).num;
        } else {
            return false;
        }

    }

    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        String initial = leftPad(Integer.toString(this.num), 9, '0');
        return initial.substring(0, 3) + '-' + initial.substring(3, 5) + '-'
                + initial.substring(5);
    }

    public String toRedactedString() {
        int serial = this.num % 10000;
        String last4 = leftPad(Integer.toString(serial), 4, '0');
        return AREA_AND_GROUP_REDACTION + last4;
    }

    /**
     * Constructor.
     * @param number The number. For example, 78051120, which corresponds to
     *               078-05-1120, the Social Security Number originally issued
     *               to Hilda Schrader Whitcher, an executive secretary at E. H.
     *               Ferree, a wallet manufacturer.
     * @throws IllegalArgumentException If {@code number} is negative, or if
     * it's equal to or greater than {@link #UPPER_NUMBER_LIMIT}.
     */
    public SocialSecurityNumber(int number) {
        super(number);
    }

}
