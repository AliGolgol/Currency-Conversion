package com.currencyconversion.api.services;

import com.currencyconversion.api.models.Currency;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyConversionService {

    private final Path root = Paths.get("resource");

    /**
     * Call a exchange rate from a public service, then calculate currency conversion
     * @param file is a {@link MultipartFile}
     * @param currencyConversion is a {@link String}
     * @return a {@link String}
     */
    public String convertCurrency(MultipartFile file, String currencyConversion) {
        List<Currency> currencies = new ArrayList<>();
        BufferedReader reader;
        Gson gson = new Gson();

        try {
            if (Files.list(root) != null) {
                FileUtils.deleteDirectory(root.toFile());
                Files.createDirectory(root);
            }

            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            reader = new BufferedReader(new FileReader("resource/input.json"));
            reader.lines().forEach(x -> {
                Currency currency = gson.fromJson(x, Currency.class);
                Currency result = calculateCurrency(currency.getValue(), currency.getCurrency(), currencyConversion);
                currencies.add(result);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = gson.toJson(currencies);
        return s;
    }

    private Currency calculateCurrency(String value, String from, String to) {
        MonetaryAmount baseCurrency = Monetary.getDefaultAmountFactory().setCurrency(from)
                .setNumber(Double.parseDouble(value)).create();

        CurrencyConversion conversion = MonetaryConversions.getConversion(to);

        MonetaryAmount convertedAmountUSDtoEUR = baseCurrency.with(conversion);
        System.out.println(convertedAmountUSDtoEUR.getNumber());
        System.out.println(convertedAmountUSDtoEUR);

        return Currency.builder()
                .currency(from)
                .value(convertedAmountUSDtoEUR.getNumber().toString()).build();

    }

}
