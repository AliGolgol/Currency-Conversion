package com.currencyconversion.cli.services;

import com.currencyconversion.cli.models.Currency;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ApiProxyTest {

    ApiProxy apiProxy;

    @Before
    public void setup(){
        apiProxy = new ApiProxyImpl();
    }

    @Test
    public void should_returnListOfCurrencies_whenApiIsAvailable(){

        List<Currency> currencies = apiProxy.send("/Users/aligolgol/Desktop/input.json", "EUR");

        assertEquals(3,currencies.size());
    }
}