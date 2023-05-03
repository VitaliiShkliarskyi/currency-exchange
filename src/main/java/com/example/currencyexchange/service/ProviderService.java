package com.example.currencyexchange.service;

import com.example.currencyexchange.model.ExchangeRate;
import java.time.LocalDate;
import java.util.List;

public interface ProviderService {
    void syncExchangeRate();

    List<ExchangeRate> getAll();

    List<ExchangeRate> getAllByDate(LocalDate date);

    List<ExchangeRate> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
