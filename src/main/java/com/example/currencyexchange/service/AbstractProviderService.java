package com.example.currencyexchange.service;

import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import java.time.LocalDate;
import java.util.List;

public abstract class AbstractProviderService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final String providerName;

    protected AbstractProviderService(ExchangeRateRepository exchangeRateRepository,
                                      String providerName) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.providerName = providerName;
    }

    public List<ExchangeRate> getAllByDate(LocalDate date) {
        return exchangeRateRepository.findAllByProviderAndDate(providerName, date);
    }

    public List<ExchangeRate> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return exchangeRateRepository
                .findAllByProviderAndDateBetween(providerName, dateFrom, dateTo);
    }

    protected void saveUniqueExchangeRate(ExchangeRate rateToSave) {
        ExchangeRate existingRate = exchangeRateRepository.findByDateAndCurrencyAndProvider(
                rateToSave.getDate(), rateToSave.getCurrency(), rateToSave.getProvider());
        if (existingRate != null) {
            existingRate.setPurchaseRate(rateToSave.getPurchaseRate());
            existingRate.setSaleRate(rateToSave.getSaleRate());
            exchangeRateRepository.save(existingRate);
        } else {
            exchangeRateRepository.save(rateToSave);
        }
    }
}
