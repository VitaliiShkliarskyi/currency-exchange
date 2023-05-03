package com.example.currencyexchange.service.mapper.api;

import com.example.currencyexchange.model.ExchangeRate;

public interface ProviderMapper <T> {
    ExchangeRate parseApiExchangeDto(T dto);
}
