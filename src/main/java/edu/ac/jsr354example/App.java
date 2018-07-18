package edu.ac.jsr354example;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.money.format.MonetaryAmountFormat;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);


    }
}
