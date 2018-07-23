package edu.ac.jsr354example.service;

import edu.ac.jsr354example.domain.Value;
import edu.ac.jsr354example.provider.CryptoCoinExchangeRateProvider;
import edu.ac.jsr354example.repository.ValueRepository;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

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

    public String convert(MonetaryAmount monetaryAmount, String format) {

        ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider();

        if(containsOurOwnCurrency(monetaryAmount, format)){
            // The line below was supposed to be:
            // rateProvider = MonetaryConversions.getExchangeRateProvider("CryptoCoinExchangeProvider");
            // but I'm getting: Invalid ExchangeRateProvider (not found)
            rateProvider = new CryptoCoinExchangeRateProvider();
        }

        return rateProvider.getCurrencyConversion( Monetary.getCurrency("BRL") ).apply(monetaryAmount).toString();
    }

    private boolean containsOurOwnCurrency(MonetaryAmount monetaryAmount, String format) {
        return monetaryAmount.getCurrency().getCurrencyCode().equals("CCC") || format.equals("CCC");
    }

    public static void main(String[] args) {
        MoneyQueuerService service = new MoneyQueuerService();

        System.out.println(service.convert(Money.of(250, CurrencyUnitBuilder.of("CCC", "CryptoCoinProvider").build()), "BRL"));

    }
}
