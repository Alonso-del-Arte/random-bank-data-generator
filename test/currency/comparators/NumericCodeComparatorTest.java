package currency.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NumericCodeComparatorTest {

    private static final Set<Currency> ALL_CURRENCIES
            = Currency.getAvailableCurrencies();

    @Test
    public void testCompare() {
        int initialCapacity = ALL_CURRENCIES.size();
        List<Currency> expected = new ArrayList<>(initialCapacity);
        Map<Integer, Currency> map = new TreeMap<>();
        for (Currency currency : ALL_CURRENCIES) {
            map.put(currency.getNumericCode(), currency);
        }
        SortedSet<Integer> keys = new TreeSet<>(map.keySet());
        for (int key : keys) {
            expected.add(map.get(key));
        }
        List<Currency> actual = new ArrayList<>(expected);
        Collections.shuffle(actual);
        actual.sort(new NumericCodeComparator());
        assertEquals(expected, actual);
    }

}
