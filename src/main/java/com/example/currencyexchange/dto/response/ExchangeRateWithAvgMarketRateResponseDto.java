package com.example.currencyexchange.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class ExchangeRateWithAvgMarketRateResponseDto {
    private List<ExchangeRateResponseDto> exchangeRates;
    private List<MarketRateResponseDto> marketRates;
}
