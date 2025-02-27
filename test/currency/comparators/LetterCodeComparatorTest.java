package currency.comparators;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LetterCodeComparatorTest {

    private static final Set<Currency> ALL_CURRENCIES
            = Currency.getAvailableCurrencies();

    @Test
    public void testCompare() {
        int initialCapacity = ALL_CURRENCIES.size();
        List<Currency> expected = new ArrayList<>(initialCapacity);
        List<Currency> actual = new ArrayList<>(ALL_CURRENCIES);
        String[] letterCodes = new String[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            letterCodes[i] = actual.get(i).getCurrencyCode();
        }
        Arrays.sort(letterCodes);
        for (int j = 0; j < initialCapacity; j++) {
            expected.add(Currency.getInstance(letterCodes[j]));
        }
        actual.sort(new LetterCodeComparator());
        assertEquals(expected, actual);
    }

}
