package edu.ac.jsr354example.provider;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencyContext;
import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CryptoCoinCurrencyProvider implements CurrencyProviderSpi{
    private final String PROVIDER_NAME = "CryptoCoinProvider";

    private Set<CurrencyUnit> currencies;
    private CurrencyContext context = CurrencyContextBuilder.of(PROVIDER_NAME).build();



    public CryptoCoinCurrencyProvider(){
        currencies = new HashSet<>();
        currencies.add(CurrencyUnitBuilder.of("CCC",context ).setDefaultFractionDigits(10).build());
        currencies = Collections.unmodifiableSet(currencies);
    }

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        if(query.getCurrencyCodes().contains("CCC") || query.getCurrencyCodes().isEmpty()){
            return currencies;
        }
        return Collections.emptySet();
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean isCurrencyAvailable(CurrencyQuery query) {
        return true;
    }
}
