package com.codingshuttle.currencyconverter.controller;

import com.codingshuttle.currencyconverter.clients.CurrencyClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CurrencyController {
    private final CurrencyClient client;

    public CurrencyController(CurrencyClient client) {
        this.client = client;
    }

   @GetMapping("/convertCurrency")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double units
    ){
        double result= client.getConvertedCurrency(fromCurrency,toCurrency,units);
        return String.format("%f %s = %f %s", units, fromCurrency, result, toCurrency);
    }



}
