package currency.conversions;

import currency.CurrencyChooser;
import currency.CurrencyPair;
import currency.SpecificCurrenciesSupport;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HardCodedRateProviderTest {

    private static final Currency UNITED_STATES_DOLLARS
            = Currency.getInstance(Locale.US);

    private static final String USD_DISPLAY_NAME
            = UNITED_STATES_DOLLARS.getDisplayName();

    private static final String USD_3_LETTER_CODE
            = UNITED_STATES_DOLLARS.getCurrencyCode();

    @Test
    public void testDateOfHardCodingConstant() {
        LocalDate expected = LocalDate.of(2025, Month.MARCH, 3);
        LocalDate actual = HardCodedRateProvider.DATE_OF_HARD_CODING;
        assertEquals(actual, expected);
    }

    @Test
    public void testSupportedCurrencies() {
        SpecificCurrenciesSupport instance = new HardCodedRateProvider();
        String[] currencyCodes = {"AUD", "BRL", "CAD", "CNY", "EUR", "GBP",
                "HKD", "ILS", "INR", "JPY", "KRW", "MXN", "NZD", "PHP", "TWD",
                "USD", "VND", "XAF", "XCD", "XOF", "XPF"};
        Set<Currency> expected = Set.of(currencyCodes).stream()
                .map(Currency::getInstance).collect(Collectors.toSet());
        Set<Currency> actual = instance.supportedCurrencies();
        assertEquals(expected, actual);
    }

    @Test
    public void testSupportedCurrenciesDoesNotLeakField() {
        SpecificCurrenciesSupport instance = new HardCodedRateProvider();
        Set<Currency> initial = instance.supportedCurrencies();
        Currency currency = CurrencyChooser.chooseCurrency(
                cur -> !initial.contains(cur)
        );
        String message = "Trying to add " + currency.getDisplayName() + " ("
                + currency.getCurrencyCode()
                + ") to reported set should not leak field nor cause exception";
        assertDoesNotThrow(() -> {
            Set<Currency> expected = new HashSet<>(initial);
            initial.add(currency);
            Set<Currency> actual = instance.supportedCurrencies();
            assertEquals(expected, actual, message);
        }, message);
    }

    @Test
    public void testGetRateUSDToAUD() {
        ExchangeRateProvider instance = new HardCodedRateProvider();
        Currency austrDollar = Currency.getInstance("AUD");
        double minimum = 1.44533;
        double actual = instance.getRate(UNITED_STATES_DOLLARS, austrDollar);
        double maximum = 1.64034;
        String msg = "Rate of conversion " + actual + " from "
                + USD_DISPLAY_NAME + " (" + USD_3_LETTER_CODE + ") to "
                + austrDollar.getDisplayName() + " ("
                + austrDollar.getCurrencyCode() + ") should be more than "
                + minimum + " but less than " + maximum;
        assert minimum < actual && actual < maximum : msg;
    }

}
