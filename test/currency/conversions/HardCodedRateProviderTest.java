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

}
