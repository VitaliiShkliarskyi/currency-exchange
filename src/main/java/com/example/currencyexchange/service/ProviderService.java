package com.example.currencyexchange.service;

import java.time.LocalDate;
import java.util.List;
import com.example.currencyexchange.model.ExchangeRate;

public interface ProviderService {
    void syncExchangeRate();

    List<ExchangeRate> getAll();

    List<ExchangeRate> getAllByDate(LocalDate date);

    List<ExchangeRate> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
