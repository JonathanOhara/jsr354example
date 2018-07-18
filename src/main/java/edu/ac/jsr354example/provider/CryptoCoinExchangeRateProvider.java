package edu.ac.jsr354example.provider;


import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.NumberValue;
import javax.money.convert.*;

public class CryptoCoinExchangeRateProvider extends AbstractRateProvider {

    private CurrencyUnit BRL = Monetary.getCurrency("BRL");
    private CurrencyUnit CCC = Monetary.getCurrency("CCC");

    private static final ProviderContext context = ProviderContextBuilder.of("CryptoCoinProvider", RateType.ANY ).build();


    public CryptoCoinExchangeRateProvider(){
        super(context);
    }

    public CryptoCoinExchangeRateProvider( final ProviderContext providerContext ){
        super(providerContext);
    }


    @Override
    public ExchangeRate getExchangeRate(final ConversionQuery conversionQuery){
        CurrencyUnit from = conversionQuery.getBaseCurrency();
        CurrencyUnit to = conversionQuery.getCurrency();

        if(from.equals(CCC) && to.equals(CCC) )
            return builderExchangeRate(from, to, 1.0f);
        else if(from.equals(CCC) && to.equals(BRL) )
            return builderExchangeRate(from, to, 0.5f);
        else if(from.equals(BRL) && to.equals(CCC) )
            return builderExchangeRate(from, to, 2.0f);

        return null;
    }

    private ExchangeRate builderExchangeRate(CurrencyUnit from, CurrencyUnit to,final Number multiplier) {
        ExchangeRateBuilder builder = new ExchangeRateBuilder( ConversionContextBuilder.create(getContext(), RateType.ANY).build() );

        return builder.setBase(from).
                setTerm(to).
                setFactor(DefaultNumberValue.of(multiplier)).
                build();
    }

}
