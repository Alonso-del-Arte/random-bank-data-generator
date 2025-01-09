package currency;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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

    static final Random RANDOM = new Random();

    private static final Set<Currency> ALL_CURRENCIES
            = Currency.getAvailableCurrencies();

    private static final int MAX_NUMBER_OF_PREDICATE_MATCH_ATTEMPTS
            = 3 * ALL_CURRENCIES.size();

    private static final List<Currency> CURRENCIES
            = new ArrayList<>(ALL_CURRENCIES);

    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();

    private static final List<Currency> PSEUDO_CURRENCIES_LIST;

    private static final Set<Currency> HISTORICAL_CURRENCIES = new HashSet<>();

    private static final Set<Currency> OTHER_EXCLUSIONS = new HashSet<>();

    private static final String[] OTHER_EXCLUSION_CODES = {"ADP", "ATS", "AYM",
            "BEF", "BGL", "BOV", "CHE", "CHW", "CYP", "DEM", "EEK", "ESP",
            "FIM", "FRF", "GRD", "IEP", "ITL", "LUF", "MTL", "NLG", "PTE",
            "SIT", "USN", "USS"};

    private static final Map<Integer, Set<Currency>> CURRENCIES_DIGITS_MAP
            = new HashMap<>();

    static {
        final String nineteenthCenturyYearIndicator = "(18";
        final String twentiethCenturyYearIndicator = "(19";
        final String twentyFirstCenturyYearIndicator = "(20";
        for (Currency currency : CURRENCIES) {
            int fractionDigits = currency.getDefaultFractionDigits();
            if (fractionDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                String dispName = currency.getDisplayName();
                if (dispName.contains(nineteenthCenturyYearIndicator)
                        || dispName.contains(twentiethCenturyYearIndicator)
                        || dispName.contains(twentyFirstCenturyYearIndicator))
                {
                    HISTORICAL_CURRENCIES.add(currency);
                } else {
                    Set<Currency> digitGroupedSet;
                    if (CURRENCIES_DIGITS_MAP.containsKey(fractionDigits)) {
                        digitGroupedSet = CURRENCIES_DIGITS_MAP
                                .get(fractionDigits);
                    } else {
                        digitGroupedSet = new HashSet<>();
                        CURRENCIES_DIGITS_MAP.put(fractionDigits,
                                digitGroupedSet);
                    }
                    if (Arrays.binarySearch(OTHER_EXCLUSION_CODES,
                            currency.getCurrencyCode()) < 0) {
                        digitGroupedSet.add(currency);
                    }
                }
            }
        }
        for (String exclusionCode : OTHER_EXCLUSION_CODES) {
            try {
                Currency currency = Currency.getInstance(exclusionCode);
                OTHER_EXCLUSIONS.add(currency);
            } catch (IllegalArgumentException iae) {
                System.err.println("\"" + iae.getMessage() + "\"");
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
        CURRENCIES.removeAll(HISTORICAL_CURRENCIES);
        CURRENCIES.removeAll(OTHER_EXCLUSIONS);
        PSEUDO_CURRENCIES_LIST = new ArrayList<>(PSEUDO_CURRENCIES);
    }

    public static Set<Currency> getSuitableCurrencies() {
        return new HashSet<>(CURRENCIES);
    }

    public static boolean isSuitableCurrency(Currency currency) {
        return CURRENCIES.contains(currency);
    }

    public static Currency choosePseudocurrency() {
        int index = RANDOM.nextInt(PSEUDO_CURRENCIES_LIST.size());
        return PSEUDO_CURRENCIES_LIST.get(index);
    }

    public static Currency chooseCurrency() {
        int index = RANDOM.nextInt(CURRENCIES.size());
        return CURRENCIES.get(index);
    }

    /**
     * Chooses a currency with a specified number of default fraction digits.
     * Thus, a currency that divides into 100 cents (corresponding to 2 default
     * fraction digits) can be chosen.
     * @param fractionDigits How many fraction digits the currency should have.
     * Examples: 2, 3, 0, 4, 7, &minus;6.
     * @return A currency with the specified number of fraction digits.
     * Examples: For 2, the Guatemalan quetzal (GTQ), one of which divides into
     * 100 centavos; for 3, the Omani rial (OMR), one of which divides into
     * 1,000 baisa; for 0, the Luxembourgian franc, one of which does not
     * normally divide into any kind of cent, unlike the Swiss franc; and for 4,
     * the only available option might be the Chilean unit of account (CLF),
     * which, however, does not have any kind of circulating bills or coins
     * associated with it.
     * @throws NoSuchElementException If there are no currencies for
     * {@code fractionDigits}. In the examples given above, 7 would almost
     * certainly cause this exception, as such a currency is unlikely to be on
     * the list of available currencies, and &minus;6 definitely would, as
     * {@code Currency} instances with negative default fraction digits are
     * ignored by this chooser.
     */
    public static Currency chooseCurrency(int fractionDigits) {
        if (CURRENCIES_DIGITS_MAP.containsKey(fractionDigits)) {
            List<Currency> currencies
                    = new ArrayList<>(CURRENCIES_DIGITS_MAP
                    .get(fractionDigits));
            int index = RANDOM.nextInt(currencies.size());
            return currencies.get(index);
        } else {
            String excMsg = "No available currency has " + fractionDigits
                    + " fraction digits";
            throw new NoSuchElementException(excMsg);
        }
    }

    /**
     * Chooses a currency according to a specified predicate.
     * @param predicate The predicate. For example, currency's display name
     * should contain the word "dollar".
     * @return A currency satisfying the predicate. For the example predicate,
     * for example, the Surinamese dollar (SRD).
     * @throws NoSuchElementException If no match for the predicate is found
     * after a reasonable number of attempts.
     */
    public static Currency chooseCurrency(Predicate<Currency> predicate) {
        boolean found = false;
        int attemptsSoFar = 0;
        Currency currency = chooseCurrency();
        while (!found
                && attemptsSoFar < MAX_NUMBER_OF_PREDICATE_MATCH_ATTEMPTS) {
            currency = chooseCurrency();
            attemptsSoFar++;
            found = predicate.test(currency);
        }
        if (found) {
            return currency;
        } else {
            String excMsg = "No currency matching predicate found after "
                    + attemptsSoFar + " attempts from pool of "
                    + CURRENCIES.size() + " suitable currencies";
            throw new NoSuchElementException(excMsg);
        }
    }

    // TODO: Write tests for this
    public static Currency chooseCurrencyOtherThan(Currency currency) {
        Currency otherCurrency = currency;
        while (otherCurrency == currency) {
            otherCurrency = chooseCurrency();
        }
        return otherCurrency;
    }

}
