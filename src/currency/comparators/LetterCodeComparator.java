package currency.comparators;

import java.util.Comparator;
import java.util.Currency;

public class LetterCodeComparator implements Comparator<Currency> {

    @Override
    public int compare(Currency currencyA, Currency currencyB) {
        return currencyA.getCurrencyCode()
                .compareTo(currencyB.getCurrencyCode());
    }

}
