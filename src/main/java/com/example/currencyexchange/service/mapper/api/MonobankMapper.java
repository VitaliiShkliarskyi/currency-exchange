package com.example.currencyexchange.service.mapper.api;

import java.time.LocalDate;
import com.example.currencyexchange.dto.external.MonobankApiExchangeRateDto;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public class MonobankMapper implements ProviderMapper<MonobankApiExchangeRateDto> {
    private static final String PROVIDER_NAME = "Monobank";
    private static final int USD_ISO_4217 = 840;
    private static final int EUR_ISO_4217 = 978;

    public ExchangeRate parseApiExchangeDto(MonobankApiExchangeRateDto dto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSaleRate(dto.getRateSell());
        exchangeRate.setPurchaseRate(dto.getRateBuy());
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setProvider(PROVIDER_NAME);
        exchangeRate.setCurrency(Currency.valueOf(convertIsoToLiteral(dto.getCurrencyCodeA())));
        return exchangeRate;
    }

    private String convertIsoToLiteral(int isoValue){
        return switch (isoValue) {
            case USD_ISO_4217 -> "USD";
            case EUR_ISO_4217 -> "EUR";
            default -> throw new RuntimeException("Currency must be USD or EUR");
        };
    }
}
