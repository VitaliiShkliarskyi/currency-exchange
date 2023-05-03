package com.example.currencyexchange.service.mapper;

import com.example.currencyexchange.dto.ExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapper implements ResponseMapper<ExchangeRate> {
    private final ExchangeRateRepository repository;

    public ExchangeRateMapper(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    public ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate) {
        ExchangeRateResponseDto dto = new ExchangeRateResponseDto();
        dto.setProvider(exchangeRate.getProvider());
        dto.setCurrency(exchangeRate.getCurrency().name());
        dto.setPurchaseRate(exchangeRate.getPurchaseRate());
        dto.setSaleRate(exchangeRate.getSaleRate());
        dto.setAvgMarketSaleRate(repository
                .getAverageMarketSaleRate(exchangeRate.getCurrency(), exchangeRate.getDate()));
        dto.setAvgMarketPurchaseRate(repository
                .getAverageMarketPurchaseRate(exchangeRate.getCurrency(), exchangeRate.getDate()));
        dto.setDate(exchangeRate.getDate().toString());
        return dto;
    }
}
