package com.currencyconversion.cli.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Currency {
    private String value;
    private String currency;

    @Override
    public String toString() {
        return
                " {\"value\" :" + value +
                ", \"currency\" :" + currency +
                '}';
    }
}
