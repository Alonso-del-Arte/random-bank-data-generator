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
            = Set.of(CURRENCY_CODES).stream().map(
            currencyCode -> Currency.getInstance(currencyCode)
    ).collect(Collectors.toSet());

    @Override
    public Set<Currency> supportedCurrencies() {
        return SUPPORTED_CURRENCIES;
    }


    // TODO: Write tests for this
    @Override
    public double getRate(Currency source, Currency target) {
        return -1.0;
    }

}
