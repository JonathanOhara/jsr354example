package edu.ac.jsr354example;

import edu.ac.jsr354example.provider.CryptoCoinExchangeRateProvider;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        testNewCurrency();
        SpringApplication.run(App.class, args);
    }

    public static void testNewCurrency(){
        ExchangeRateProvider rateProvider;

        CurrencyUnit ccc = CurrencyUnitBuilder.of("CCC", "CryptoCoinProvider").build();
        CurrencyUnit brl = Monetary.getCurrency("BRL");


        MonetaryAmount brlMoney = Money.of(200, brl);


        // The line below was supposed to be:
        // rateProvider = MonetaryConversions.getExchangeRateProvider("CryptoCoinExchangeProvider");
        // but I'm getting: Invalid ExchangeRateProvider (not found)
        rateProvider = new CryptoCoinExchangeRateProvider();

        System.out.println(rateProvider.getCurrencyConversion(ccc).apply(brlMoney));

    }
}
