package com.example.currencyexchange.service.mapper.api;

import com.example.currencyexchange.dto.external.MinfinApiExchangeRateDto;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.model.ExchangeRate;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class MinfinMapper implements ProviderMapper<MinfinApiExchangeRateDto> {
    private static final String PROVIDER_NAME = "Minfin";

    public ExchangeRate parseApiExchangeDto(MinfinApiExchangeRateDto dto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSaleRate(Float.parseFloat(dto.getAsk()));
        exchangeRate.setPurchaseRate(Float.parseFloat(dto.getBid()));
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setProvider(PROVIDER_NAME);
        exchangeRate.setCurrency(Currency.valueOf(dto.getCurrency().toUpperCase()));
        return exchangeRate;
    }
}
