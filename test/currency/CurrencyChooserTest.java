package currency;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CurrencyChooserTest {

    private static final Set<Currency> CURRENCIES
            = Currency.getAvailableCurrencies();

    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();

    private static final Map<Integer, Set<Currency>> FRACT_DIGITS_MAP
            = new HashMap<>();

    private static final Currency DOLLARS = Currency.getInstance(Locale.US);

    private static final int TOTAL_NUMBER_OF_CURRENCIES = CURRENCIES.size();

    private static final int NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH
            = 4;

    private static final int NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH
            = NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH
            * TOTAL_NUMBER_OF_CURRENCIES;

    private static final String[] EURO_REPLACED_EXCLUSION_CODES = {/* "ADP", "ATS",
            "BEF", "CYP", "DEM", "EEK", "ESP", "FIM", "FRF", "GRD", "IEP", "ITL",
            "LUF", "MTL", "NLG", "PTE", "SIT" */};

    private static final String[] OTHER_EXCLUSION_CODES = {};

    static {
        for (Currency currency : CURRENCIES) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                Set<Currency> digitGroupedSet;
                if (FRACT_DIGITS_MAP.containsKey(fractDigits)) {
                    digitGroupedSet = FRACT_DIGITS_MAP.get(fractDigits);
                } else {
                    digitGroupedSet = new HashSet<>();
                    FRACT_DIGITS_MAP.put(fractDigits, digitGroupedSet);
                }
                digitGroupedSet.add(currency);
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
    }

    private static boolean isEuroReplacedCurrency(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(EURO_REPLACED_EXCLUSION_CODES, key) > -1;
    }

    private static boolean isHistoricalCurrency(Currency currency) {
        String displayName = currency.getDisplayName();
        return displayName.contains("(18")
                || displayName.contains("(19")
                || displayName.contains("(20")
                || isEuroReplacedCurrency(currency);
    }

    private static boolean isPseudoCurrency(Currency currency) {
        return currency.getDefaultFractionDigits() < 0;
    }

    private static boolean shouldOtherwiseBeExcluded(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(OTHER_EXCLUSION_CODES, key) > -1;
    }

    private static boolean accept(Currency currency) {
        return !isHistoricalCurrency(currency) && !isPseudoCurrency(currency)
                && !shouldOtherwiseBeExcluded(currency);
    }

    @Test
    public void testGetSuitableCurrencies() {
        System.out.println("getSuitableCurrencies");
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        Set<Currency> expected = currencies.stream()
                .filter(CurrencyChooserTest::accept)
                .collect(Collectors.toSet());
        Set<Currency> actual = CurrencyChooser.getSuitableCurrencies();
        assertEquals(actual, expected);
    }

    @Test
    public void testIsSuitableCurrency() {
        System.out.println("isSuitableCurrency");
        Set<Currency> currencies = CurrencyChooser.getSuitableCurrencies();
        for (Currency currency : currencies) {
            String msg = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode()
                    + ") should be considered suitable";
            assert CurrencyChooser.isSuitableCurrency(currency) : msg;
        }
    }

    @Test
    public void testIsNotSuitableCurrency() {
        Set<Currency> complement = new HashSet<>(CURRENCIES);
        Set<Currency> suitables = CurrencyChooser.getSuitableCurrencies();
        complement.removeAll(suitables);
        for (Currency currency : complement) {
            String msg = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode()
                    + ") should not be considered suitable";
            assert !CurrencyChooser.isSuitableCurrency(currency) : msg;
        }
    }

}
