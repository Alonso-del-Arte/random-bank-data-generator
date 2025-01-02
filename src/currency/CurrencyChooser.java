package currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class CurrencyChooser {

    // TODO: Write tests for this
    public static Set<Currency> getSuitableCurrencies() {
        return new HashSet<>();
    }

    // TODO: Write tests for this
    public static boolean isSuitableCurrency(Currency currency) {
        return false;
    }

    // TODO: Write tests for this
    public static Currency choosePseudocurrency() {
        return Currency.getInstance("USD");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(int fractionDigits) {
        return Currency.getInstance("EUR");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(Predicate<Currency> predicate) {
        return Currency.getInstance("XCD");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrencyOtherThan(Currency currency) {
        return currency;
    }

}
