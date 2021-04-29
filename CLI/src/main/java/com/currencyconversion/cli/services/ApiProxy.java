package com.currencyconversion.cli.services;

import com.currencyconversion.cli.models.Currency;

import java.util.List;

public interface ApiProxy {
    List<Currency> send(String file, String targetCurrency);
}
