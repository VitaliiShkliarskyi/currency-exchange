package com.example.currencyexchange.model;

public enum Currency {
    USD("USD"),
    EUR("EUR");
    private String value;

    Currency(String value) {
        this.value = value;
    }
}
