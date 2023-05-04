package com.example.currencyexchange.service.mapper.response;

import com.example.currencyexchange.dto.response.AvgExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public class AvgExchangeRateDtoMapper implements
        ResponseMapper<ExchangeRate, AvgExchangeRateResponseDto> {
    @Override
    public AvgExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate) {
        AvgExchangeRateResponseDto dto = new AvgExchangeRateResponseDto();
        dto.setProvider(exchangeRate.getProvider());
        dto.setCurrency(exchangeRate.getCurrency().name());
        dto.setAvgRate((exchangeRate.getPurchaseRate() + exchangeRate.getSaleRate()) / 2);
        dto.setDate(exchangeRate.getDate().toString());
        return dto;
    }
}
