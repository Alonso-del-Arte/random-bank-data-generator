package currency;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
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

    private static final String[] EURO_REPLACED_EXCLUSION_CODES = {"ADP", "ATS",
            "BEF", "CYP", "DEM", "EEK", "ESP", "FIM", "FRF", "GRD", "IEP",
            "ITL", "LUF", "MTL", "NLG", "PTE", "SIT"};

    private static final String[] OTHER_EXCLUSION_CODES = {"AYM", "BGL", "BOV",
            "CHE", "CHW", "COU", "GWP", "MGF", "MXV", "SRG", "STN", "TPE",
            "USN", "USS"};

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

    private static boolean isNotHistoricalCurrency(Currency currency) {
        String displayName = currency.getDisplayName();
        return !displayName.contains("(18")
                && !displayName.contains("(19")
                && !displayName.contains("(20")
                && !isEuroReplacedCurrency(currency);
    }

    private static boolean isPseudoCurrency(Currency currency) {
        return currency.getDefaultFractionDigits() < 0;
    }

    private static boolean shouldOtherwiseBeExcluded(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(OTHER_EXCLUSION_CODES, key) > -1;
    }

    private static boolean accept(Currency currency) {
        return isNotHistoricalCurrency(currency) && !isPseudoCurrency(currency)
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
        Set<Currency> difference = new HashSet<>(expected);
        difference.removeAll(actual);
        Set<Currency> diffRevDir = new HashSet<>(actual);
        diffRevDir.removeAll(expected);
        difference.addAll(diffRevDir);
        String message = "Currencies found in one set but not the other were "
                + difference;
        assertEquals(expected, actual, message);
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

    @Test
    public void testChoosePseudocurrency() {
        System.out.println("choosePseudocurrency");
        int initialCapacity = PSEUDO_CURRENCIES.size();
        int numberOfCalls = initialCapacity * 10;
        Set<Currency> actual = new HashSet<>(initialCapacity);
        for (int i = 0; i < numberOfCalls; i++) {
            Currency pseudocurrency = CurrencyChooser.choosePseudocurrency();
            actual.add(pseudocurrency);
        }
        assertEquals(PSEUDO_CURRENCIES, actual);
    }

    @Test
    public void testChooseCurrency() {
        System.out.println("chooseCurrency");
        int totalNumberOfCurrencies = CURRENCIES.size();
        int numberOfTries = 5 * totalNumberOfCurrencies / 3;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrency();
            String msg = "Chosen currency " + sample.getDisplayName()
                    + " expected to not have negative fraction digits";
            assert sample.getDefaultFractionDigits() > -1 : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 11 * totalNumberOfCurrencies / 20;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " times from set of "
                + totalNumberOfCurrencies + " gave " + actual
                + " distinct, should've given more than " + expected
                + " distinct";
        assert expected <= actual : msg;
    }

    @Test
    public void testChooseCurrencyOtherThanDollars() {
        int numberOfTries = 40;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        String dollarsDisplayName = DOLLARS.getDisplayName();
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrencyOtherThan(DOLLARS);
            String msg = "Chosen currency " + sample.getDisplayName()
                    + " expected to not be " + dollarsDisplayName;
            assert sample != DOLLARS : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 11 * numberOfTries / 20;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " other than "
                + dollarsDisplayName + " gave " + actual
                + " distinct, should've given at least " + expected
                + " distinct";
        assert expected <= actual : msg;
    }

    @Test
    public void testChooseCurrencyOtherThan() {
        System.out.println("chooseCurrencyOtherThan");
        Currency someCurrency
                = CurrencyChooser.chooseCurrencyOtherThan(DOLLARS);
        int numberOfTries = 40;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        String currencyDisplayName = someCurrency.getDisplayName();
        while (sampleNumber < numberOfTries) {
            Currency sample
                    = CurrencyChooser.chooseCurrencyOtherThan(someCurrency);
            String msg = "Chosen currency " + sample.getDisplayName()
                    + " expected to not be " + currencyDisplayName;
            assert sample != someCurrency : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 11 * numberOfTries / 20;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " other than "
                + currencyDisplayName + " gave " + actual
                + " distinct, should've given more than " + expected
                + " distinct";
        assert expected < actual : msg;
    }

    @Test
    public void testChooseCurrencyWithNoCentsOrDarahim() {
        int expected = 0;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String message = "Chosen currency " + currency.getDisplayName()
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, message);
    }

    @Test
    public void testChooseCurrencyWith100Cents() {
        int expected = 2;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String message = "Chosen currency " + currency.getDisplayName()
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, message);
    }

    @Test
    public void testChooseCurrencyWith1000Darahim() {
        int expected = 3;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String message = "Chosen currency " + currency.getDisplayName()
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, message);
    }

    @Test
    public void testChooseCurrencyWith10000Divisions() {
        int expected = 4;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String message = "Chosen currency " + currency.getDisplayName()
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, message);
    }

    @Test
    public void testUnavailableFractionDigitsCauseException() {
        Random random = new Random();
        int bound = 128;
        int unlikelyFractionDigits = bound + random.nextInt(bound);
        String msg = "Asking for currency with " + unlikelyFractionDigits
                + " fraction digits should cause exception";
        Throwable t = assertThrows(NoSuchElementException.class, () -> {
            Currency badCurrency
                    = CurrencyChooser.chooseCurrency(unlikelyFractionDigits);
            System.out.println("Somehow asking for currency with "
                    + unlikelyFractionDigits + " fraction digits gave "
                    + badCurrency.getDisplayName() + " ("
                    + badCurrency.getCurrencyCode() + "), which only has "
                    + badCurrency.getDefaultFractionDigits()
                    + " fraction digits");
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        System.out.println("\"" + excMsg + "\"");
        String digitString = Integer.toString(unlikelyFractionDigits);
        String containsMsg = "Exception message should include \"" + digitString
                + "\"";
        assert excMsg.contains(digitString) : containsMsg;
    }

    @Test
    public void testChooseNoCentsCurrencyRandomlyEnough() {
        int fractionDigits = 0;
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(fractionDigits);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        for (int i = 0; i < total; i++) {
            Currency currency = CurrencyChooser.chooseCurrency(fractionDigits);
            String message = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ") expected to have "
                    + fractionDigits + " fraction digits";
            assertEquals(currency.getDefaultFractionDigits(), fractionDigits,
                    message);
            chosenCurrencies.add(currency);
        }
        int minimum = total / 3;
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total
                + " currencies with no divisions, at least " + minimum
                + " should've been chosen, " + actual + " were chosen";
        assert actual >= minimum : msg;
    }

    @Test
    public void testChooseCentCurrencyRandomlyEnough() {
        int fractionDigits = 2;
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(fractionDigits);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        int maxCallCount = total / 3;
        for (int i = 0; i < maxCallCount; i++) {
            Currency currency = CurrencyChooser.chooseCurrency(fractionDigits);
            String message = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ") expected to have "
                    + fractionDigits + " fraction digits";
            assertEquals(currency.getDefaultFractionDigits(), fractionDigits,
                    message);
            chosenCurrencies.add(currency);
        }
        int minimum = maxCallCount / 8;
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total
                + " currencies dividing into 100 cents, at least " + minimum
                + " should've been chosen after " + maxCallCount + " calls, "
                + actual + " were chosen";
        assert actual >= minimum : msg;
    }

    @Test
    public void testChooseDarahimCurrencyRandomlyEnough() {
        int fractionDigits = 3;
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(fractionDigits);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        for (int i = 0; i < total; i++) {
            Currency currency = CurrencyChooser.chooseCurrency(fractionDigits);
            String message = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode() + ") expected to have "
                    + fractionDigits + " fraction digits";
            assertEquals(currency.getDefaultFractionDigits(), fractionDigits,
                    message);
            chosenCurrencies.add(currency);
        }
        int minimum = total / 2;
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total
                + " currencies dividing into 1,000 darahim, at least "
                + minimum + " should've been chosen, " + actual
                + " were chosen";
        assert actual >= minimum : msg;
    }

    @Test
    public void testChooseCurrencyByPredicate() {
        int remainder = ((int) System.currentTimeMillis()) % 16;
        Predicate<Currency> predicate
                = (currency) -> currency.getNumericCode() % 16 == remainder;
        Set<Currency> expected = CURRENCIES.stream().filter(predicate)
                .filter(CurrencyChooserTest::accept)
                .collect(Collectors.toSet());
        Set<Currency> actual = new HashSet<>();
        String msg = "Choosing currencies with numeric code " + remainder
                + " modulo 16";
        int totalNumberOfCalls = 20 * expected.size();
        int callsSoFar = 0;
        while (callsSoFar < totalNumberOfCalls) {
            actual.add(CurrencyChooser.chooseCurrency(predicate));
            callsSoFar++;
        }
        assertEquals(expected, actual, msg);
    }

    @Test
    public void testChooseCurrencyByBadPredicateCausesException() {
        String invalidDisplayName = "Invalid display name "
                + System.currentTimeMillis();
        Predicate<Currency> predicate
                = (Currency cur) -> cur.getDisplayName()
                .equals(invalidDisplayName);
        Duration allottedTime = Duration.of(10, ChronoUnit.SECONDS);
        String msg = "Bad predicate for invalid display name \""
                + invalidDisplayName + "\" should not take more than "
                + allottedTime.toString() + " to cause exception";
        assertTimeoutPreemptively(allottedTime, () -> {
            Throwable t = assertThrows(NoSuchElementException.class, () -> {
                Currency currency = CurrencyChooser.chooseCurrency(predicate);
                System.out.println("Search for \"" + invalidDisplayName
                        + "\" somehow gave " + currency.getDisplayName() + " ("
                        + currency.getCurrencyCode() + ")");
            }, msg);
            String excMsg = t.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !excMsg.isBlank() : "Exception message should not be blank";
            System.out.println("\"" + excMsg + "\"");
        }, msg);
    }

    @Test
    public void testHistoricalCurrenciesExcluded() {
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName() + " ("
                    + currency.getCurrencyCode()
                    + ") should not be a historical currency";
            assert isNotHistoricalCurrency(currency) : msg;
        }
    }

    @Test
    public void testHistoricalCurrenciesExcludedFromFractDigitSpecs() {
        for (int places = 2; places < 5; places++) {
            for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
                Currency currency = CurrencyChooser.chooseCurrency(places);
                String msg = "Currency " + currency.getDisplayName() + " ("
                        + currency.getCurrencyCode() + ") with " + places
                        + " places should not be a historical currency";
                assert isNotHistoricalCurrency(currency) : msg;
            }
        }
    }

    @Test
    public void testSameDayUSDollarExcluded() {
        Currency sameDayDollar = Currency.getInstance("USS");
        String sameDayDollarDisplayName = sameDayDollar.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + sameDayDollarDisplayName;
            assertNotEquals(sameDayDollar, currency, msg);
        }
    }

    @Test
    public void testNextDayUSDollarExcluded() {
        Currency nextDayDollar = Currency.getInstance("USN");
        String nextDayDollarDisplayName = nextDayDollar.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + nextDayDollarDisplayName;
            assertNotEquals(nextDayDollar, currency, msg);
        }
    }

    @Test
    public void testImproperAzerbaijanManatExcluded() {
        Currency improperManat = Currency.getInstance("AYM");
        String improperManatDisplayName = improperManat.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + improperManatDisplayName;
            assertNotEquals(improperManat, currency, msg);
        }
    }

    @Test
    public void testBulgarianHardLevExcluded() {
        Currency bulgarianHardLev = Currency.getInstance("BGL");
        String bulgarianHardLevDisplayName = bulgarianHardLev.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + bulgarianHardLevDisplayName;
            assertNotEquals(bulgarianHardLev, currency, msg);
        }
    }

    @Test
    public void testWIREuroExcluded() {
        Currency wirEuro = Currency.getInstance("CHE");
        String wirEuroDisplayName = wirEuro.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + wirEuroDisplayName;
            assertNotEquals(wirEuro, currency, msg);
        }
    }

    @Test
    public void testWIRFrancExcluded() {
        Currency wirFranc = Currency.getInstance("CHW");
        String wirFrancDisplayName = wirFranc.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + wirFrancDisplayName;
            assertNotEquals(wirFranc, currency, msg);
        }
    }

    @Test
    public void testBolivianMVDOLExcluded() {
        Currency mvDol = Currency.getInstance("BOV");
        String mvDolDisplayName = mvDol.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + mvDolDisplayName;
            assertNotEquals(mvDol, currency, msg);
        }
    }

    @Test
    public void testColombianRealValueUnitsExcluded() {
        Currency unitsRealValue = Currency.getInstance("COU");
        String unitsDisplayName = unitsRealValue.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + unitsDisplayName;
            assertNotEquals(unitsRealValue, currency, msg);
        }
    }

    @Test
    public void testGuineaBissauPesoExcluded() {
        Currency pesos = Currency.getInstance("GWP");
        String pesosDisplayName = pesos.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + pesosDisplayName;
            assertNotEquals(pesos, currency, msg);
        }
    }

    @Test
    public void testMalagasyFrancExcluded() {
        Currency francs = Currency.getInstance("MGF");
        String francsDisplayName = francs.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + francsDisplayName;
            assertNotEquals(francs, currency, msg);
        }
    }

    @Test
    public void testMexicanInvestmentUnitsExcluded() {
        Currency unitsInvestment = Currency.getInstance("MXV");
        String unitsDisplayName = unitsInvestment.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + unitsDisplayName;
            assertNotEquals(unitsInvestment, currency, msg);
        }
    }

    @Test
    public void testSurinameseGuilderExcluded() {
        Currency surinameseGuilder = Currency.getInstance("SRG");
        String surinameseGuilderDisplayName
                = surinameseGuilder.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + surinameseGuilderDisplayName;
            assertNotEquals(surinameseGuilder, currency, msg);
        }
    }

    @Test
    public void testSaoTomeAndPrincipeDobraExcluded() {
        Currency saoTomeAndPrincipeDobra = Currency.getInstance("STN");
        String saoTomeAndPrincipeDobraDisplayName
                = saoTomeAndPrincipeDobra.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + saoTomeAndPrincipeDobraDisplayName;
            assertNotEquals(saoTomeAndPrincipeDobra, currency, msg);
        }
    }

    @Test
    public void testTimoreseEscudoExcluded() {
        Currency escudos = Currency.getInstance("TPE");
        String unitsDisplayName = escudos.getDisplayName();
        for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
            Currency currency = CurrencyChooser.chooseCurrency();
            String msg = "Currency " + currency.getDisplayName()
                    + " should not be " + unitsDisplayName;
            assertNotEquals(escudos, currency, msg);
        }
    }

    @Test
    public void testExcludeEuropeanCurrenciesReplacedByEuro() {
        for (String currencyCode : EURO_REPLACED_EXCLUSION_CODES) {
            Currency excludedCurrency = Currency.getInstance(currencyCode);
            String exclCurrDisplayName = excludedCurrency.getDisplayName();
            for (int i = 0; i < NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH; i++) {
                Currency currency = CurrencyChooser.chooseCurrency();
                String msg = "Currency " + currency.getDisplayName()
                        + " should not be " + exclCurrDisplayName;
                assertNotEquals(excludedCurrency, currency, msg);
            }
        }
    }

}
