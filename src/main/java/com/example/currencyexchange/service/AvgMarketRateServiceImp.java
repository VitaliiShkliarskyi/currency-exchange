package com.example.currencyexchange.service;

import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class AvgMarketRateServiceImp implements AvgMarkerRateService {
    private final ExchangeRateRepository repository;

    public AvgMarketRateServiceImp(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    public Float getAverageMarketSaleRate(Currency currency, LocalDate date) {
        return repository.getAverageMarketSaleRate(currency, date);
    }

    public Float getAverageMarketPurchaseRate(Currency currency, LocalDate date) {
        return repository.getAverageMarketPurchaseRate(currency, date);
    }
}
