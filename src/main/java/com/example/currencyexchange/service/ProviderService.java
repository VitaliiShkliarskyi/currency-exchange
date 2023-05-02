package com.example.currencyexchange.service;

import com.example.currencyexchange.model.ExchangeRate;

import java.util.List;

public interface ProviderService {
    void syncExchangeRate();

    List<ExchangeRate> getAll();
}
