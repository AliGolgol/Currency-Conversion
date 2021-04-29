package com.currencyconversion.api.controller;

import com.currencyconversion.api.services.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CurrencyController {

    @Autowired
    CurrencyConversionService service;

    @PostMapping("/currency/")
    public ResponseEntity<String> currency(@RequestParam("file") MultipartFile file, @RequestParam("currency") String currency){
        System.out.println("API Called");
        String result = service.convertCurrency(file, currency);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
