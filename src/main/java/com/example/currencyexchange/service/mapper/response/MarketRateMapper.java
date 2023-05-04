package com.example.currencyexchange.service.mapper.response;

import com.example.currencyexchange.dto.response.ExchangeRateResponseDto;
import com.example.currencyexchange.dto.response.MarketRateResponseDto;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.service.AvgMarkerRateService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MarketRateMapper implements
        ResponseMapper<List<ExchangeRateResponseDto>,MarketRateResponseDto> {
    private final AvgMarkerRateService marketRateService;

    public MarketRateMapper(AvgMarkerRateService marketRateService) {
        this.marketRateService = marketRateService;
    }

    public MarketRateResponseDto toResponseDto(List<ExchangeRateResponseDto> ratesList) {
        Currency currency = Currency.valueOf(ratesList.get(0).getCurrency());
        LocalDate date = LocalDate.parse(ratesList.get(0).getDate());
        MarketRateResponseDto dto = new MarketRateResponseDto();
        dto.setAvgMarketSaleRate(marketRateService.getAverageMarketSaleRate(currency, date));
        dto.setAvgMarketPurchaseRate(marketRateService.getAverageMarketPurchaseRate(currency,date));
        dto.setCurrency(currency.name());
        return dto;
    }
}
