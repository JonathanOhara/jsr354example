package edu.ac.jsr354example.controller;

import edu.ac.jsr354example.service.MoneyQueuerService;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
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
    public ResponseEntity<MonetaryAmount> push(@PathVariable("currency")String currency, @RequestBody BigDecimal value){
        MonetaryAmount moneyAmount = Money.of(value, currency);

        return new ResponseEntity<>( service.push(moneyAmount), HttpStatus.CREATED );
    }

    @GetMapping("")
    public ResponseEntity<MonetaryAmount> pull(){
        return new ResponseEntity<>( service.pull(), HttpStatus.OK );
    }


    @GetMapping("/formats/{format}")
    public ResponseEntity<String> pullFormatted(@PathVariable("format")String format){
        MonetaryAmount monetaryAmount = service.pull();

        return new ResponseEntity<>(  service.format(monetaryAmount, format), HttpStatus.OK );
    }

    @GetMapping("/conversions/{currency}")
    public ResponseEntity<MonetaryAmount> pullConverted(@PathVariable("currency")String format){
        MonetaryAmount monetaryAmount = service.pull();
        return new ResponseEntity<>( service.convert(monetaryAmount, format), HttpStatus.OK );
    }

}
