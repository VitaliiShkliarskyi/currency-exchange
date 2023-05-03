package com.example.currencyexchange.service.mapper.api;

import java.time.LocalDate;
import com.example.currencyexchange.dto.external.PrivatApiExchangeRateDto;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public class PrivatMapper implements ProviderMapper<PrivatApiExchangeRateDto> {
    private static final String PROVIDER_NAME = "PrivatBank";

    public ExchangeRate parseApiExchangeDto(PrivatApiExchangeRateDto dto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSaleRate(Float.parseFloat(dto.getSale()));
        exchangeRate.setPurchaseRate(Float.parseFloat(dto.getBuy()));
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setProvider(PROVIDER_NAME);
        exchangeRate.setCurrency(Currency.valueOf(dto.getCcy()));
        return exchangeRate;
    }
}
