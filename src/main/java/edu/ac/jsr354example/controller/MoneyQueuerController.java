package edu.ac.jsr354example.controller;

import edu.ac.jsr354example.service.MoneyQueuerService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;

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


    @GetMapping("/{currency}/formats/{format}")
    public String pullFormatted(@PathVariable("currency")String currency, @PathVariable("format")String format){
        MonetaryAmount monetaryAmount = service.pull();
        MonetaryAmountFormat moneyFormat = MonetaryFormats.getAmountFormat(format);
        return moneyFormat.format(monetaryAmount);
    }

    @GetMapping("/{currency}/conversions/{currency}")
    public String pullConverted(@PathVariable("currency")String currency, @PathVariable("currency")String format){
        MonetaryAmount monetaryAmount = service.pull();
        return service.convert(monetaryAmount, format);
    }

}
