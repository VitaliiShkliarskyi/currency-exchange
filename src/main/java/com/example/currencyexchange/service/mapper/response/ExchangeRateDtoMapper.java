package com.example.currencyexchange.service.mapper.response;

import com.example.currencyexchange.dto.response.ExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateDtoMapper implements
        ResponseMapper<ExchangeRate, ExchangeRateResponseDto> {

    @Override
    public ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate) {
        ExchangeRateResponseDto dto = new ExchangeRateResponseDto();
        dto.setProvider(exchangeRate.getProvider());
        dto.setCurrency(exchangeRate.getCurrency().name());
        dto.setPurchaseRate(exchangeRate.getPurchaseRate());
        dto.setSaleRate(exchangeRate.getSaleRate());
        dto.setDate(exchangeRate.getDate().toString());
        return dto;
    }
}
