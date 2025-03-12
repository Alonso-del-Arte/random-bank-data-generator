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

    // TODO: Write tests for this
    @Override
    public Set<Currency> supportedCurrencies() {
        return new HashSet<>();
    }

    // TODO: Write tests for this
    @Override
    public double getRate(Currency source, Currency target) {
        return -1.0;
    }

}
