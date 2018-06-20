package edu.ac.jsr354example.controller;

import edu.ac.jsr354example.service.MoneyQueuerService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.money.MonetaryAmount;
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

}
