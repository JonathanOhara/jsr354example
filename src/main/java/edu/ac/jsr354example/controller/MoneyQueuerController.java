package edu.ac.jsr354example.controller;

import edu.ac.jsr354example.service.MoneyQueuerService;
import org.apache.tomcat.jni.Local;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder;
import org.javamoney.moneta.function.MonetaryOperators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.money.*;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Currency;
import java.util.Locale;

@RestController
@RequestMapping("/queue")
public class MoneyQueuerController {

    @Autowired
    private MoneyQueuerService service;

    @PostMapping("/{currency}")
    public MonetaryAmount push(@PathVariable("currency")String currency, BigDecimal value){
        MonetaryAmount moneyAmount = Money.of(value, currency);

        return service.push(moneyAmount);
    }

    @GetMapping("/{currency}")
    public MonetaryAmount pull(@PathVariable("currency")String currency){
        return service.pull();
    }


    public static void main(String[] args) {
        CurrencyUnit brazilianReal = Monetary.getCurrency("BRL");
//        brazilianReal.getCurrencyCode();
//        brazilianReal.getDefaultFractionDigits();
//
//        System.out.println(Monetary.getCurrency("BRL"));
//
//        CurrencyUnit usDollar = Monetary.getCurrency(Locale.US);
//        System.out.println(Monetary.getCurrency(Locale.GERMANY));

//        MonetaryAmountFormat germanyFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY);
//        System.out.println(germanyFormat.format(Money.of(123.50, "EUR")));
//
//        MonetaryAmountFormat italyFormat = MonetaryFormats.getAmountFormat(Locale.ITALY);
//        System.out.println(italyFormat.format(Money.of(123.50, "EUR")));
//
//        MonetaryAmountFormat brazilFormat = MonetaryFormats.getAmountFormat(new Locale("pt", "BR"));
//        System.out.println(brazilFormat.format(Money.of(123.50, "BRL")));
//

//        System.out.println(usFormat.format(Money.of(123.50, "USD")));


//        MonetaryAmountFormat builderFormat = MonetaryAmountDecimalFormatBuilder.of("R$ ###,###.00").build();
//        System.out.println(builderFormat.format(Money.of(1000.22, "BRL")));



        MonetaryAmountFormat usFormat = MonetaryFormats.getAmountFormat(Locale.US);
        MonetaryAmount moneyParsed = Money.parse("USD25.50", usFormat);
        System.out.println(moneyParsed);

    }

}
