package currency.comparators;

import java.util.Comparator;
import java.util.Currency;

public class NumericCodeComparator implements Comparator<Currency> {

    @Override
    public int compare(Currency currencyA, Currency currencyB) {
        return Integer.compare(currencyA.getNumericCode(),
                currencyB.getNumericCode());
    }

}
