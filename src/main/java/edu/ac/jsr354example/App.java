package edu.ac.jsr354example;

import edu.ac.jsr354example.provider.CryptoCoinCurrencyProvider;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;

@SpringBootApplication
public class App {
    public static void main(String[] args) {

        testNewCurrency();

//        SpringApplication.run(App.class, args);
    }

    public static void testNewCurrency(){

        CurrencyUnit ccc = CurrencyUnitBuilder.of("CCC", "CryptoCoinProvider").build();
        CurrencyUnit brl = Monetary.getCurrency("BRL");


        ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider();

        System.out.println(rateProvider);

    }
}
