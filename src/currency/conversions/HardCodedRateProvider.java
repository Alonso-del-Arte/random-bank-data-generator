package currency.conversions;

import currency.CurrencyPair;
import currency.SpecificCurrenciesSupport;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import currency.CurrencyPair;
import currency.SpecificCurrenciesSupport;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HardCodedRateProvider implements ExchangeRateProvider,
        SpecificCurrenciesSupport {

    public static final LocalDate DATE_OF_HARD_CODING
            = LocalDate.of(2025, Month.MARCH, 3);

    private static final String[] CURRENCY_CODES = {"AUD", "BRL", "CAD", "CNY",
            "EUR", "GBP", "HKD", "ILS", "INR", "JPY", "KRW", "MXN", "NZD", "PHP",
            "TWD", "USD", "VND", "XAF", "XCD", "XOF", "XPF"};

    private static final Set<Currency> SUPPORTED_CURRENCIES
            = Set.of(CURRENCY_CODES).stream().map(Currency::getInstance)
            .collect(Collectors.toSet());

    @Override
    public Set<Currency> supportedCurrencies() {
        return new HashSet<>(SUPPORTED_CURRENCIES);
    }


    @Override
    public double getRate(Currency source, Currency target) {
        return switch (target.getCurrencyCode()) {
            case "AUD" -> 1.6116;
            case "BRL" -> 5.9909941;
            default -> 1.45;
        };
    }

}
