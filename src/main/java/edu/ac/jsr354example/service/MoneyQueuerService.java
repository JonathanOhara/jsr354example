package edu.ac.jsr354example.service;

import edu.ac.jsr354example.domain.Value;
import edu.ac.jsr354example.provider.CryptoCoinExchangeRateProvider;
import edu.ac.jsr354example.repository.ValueRepository;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Service
public class MoneyQueuerService {
    @Autowired
    protected ValueRepository repository;

    public MonetaryAmount push(MonetaryAmount money){
        Value value = new Value();
        value.setMoney(money);

        return repository.save(value).getMoney();
    }

    public MonetaryAmount pull() {
        return repository.pull().map(value -> value.getMoney()).orElse(null);
    }


    public MonetaryAmount convert(MonetaryAmount monetaryAmount, String toFormat) {
        return convert(monetaryAmount, toFormat.contains("CCC") ? CurrencyUnitBuilder.of("CCC", "CryptoCoinProvider").build() : Monetary.getCurrency(toFormat));
    }

    public MonetaryAmount convert(MonetaryAmount monetaryAmount, CurrencyUnit to) {

        ExchangeRateProvider rateProvider = null;

        if(containsOurOwnCurrency(monetaryAmount, to)){
             rateProvider = new CryptoCoinExchangeRateProvider();
            // The line below was supposed to be:
            //rateProvider = MonetaryConversions.getExchangeRateProvider("CryptoCoinExchangeProvider");
            // but I'm getting: Invalid ExchangeRateProvider (not found)

        }else{
            rateProvider = MonetaryConversions.getExchangeRateProvider();
        }

        return rateProvider.getCurrencyConversion(to).apply(monetaryAmount);
    }

    private boolean containsOurOwnCurrency(MonetaryAmount monetaryAmount, CurrencyUnit format) {
        return monetaryAmount.getCurrency().getCurrencyCode().equals("CCC") || format.getCurrencyCode().equals("CCC");
    }

    public String format(MonetaryAmount monetaryAmount, String locale) {
        MonetaryAmountFormat requestFormatted = MonetaryFormats.getAmountFormat(new Locale.Builder().setLanguageTag(locale).build());
        return requestFormatted.format(monetaryAmount);
    }
}