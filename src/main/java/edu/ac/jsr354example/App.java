package edu.ac.jsr354example;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.money.format.MonetaryAmountFormat;

@SpringBootApplication
public class App {
    public static void main(String[] args) {


        MonetaryAmountFormat builderFormat = MonetaryAmountDecimalFormatBuilder.of("R$ ###,###.00").build();

        System.out.println(builderFormat.format(Money.of(1000.22, "BRL")));
//        SpringApplication.run(App.class, args);

    }
}
