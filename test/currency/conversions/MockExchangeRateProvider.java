package currency.conversions;

import java.util.Currency;

public class MockExchangeRateProvider implements ExchangeRateProvider {

    // TODO: Write tests for this
    @Override
    public double getRate(Currency source, Currency target) {
        return 0;
    }

}
