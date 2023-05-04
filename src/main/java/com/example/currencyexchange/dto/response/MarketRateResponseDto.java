package com.example.currencyexchange.dto.response;

import lombok.Data;

@Data
public class MarketRateResponseDto {
    private String currency;
    private Float avgMarketSaleRate;
    private Float avgMarketPurchaseRate;
}
