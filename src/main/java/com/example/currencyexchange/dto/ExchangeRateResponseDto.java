package com.example.currencyexchange.dto;

import lombok.Data;

@Data
public class ExchangeRateResponseDto {
    private String provider;
    private String currency;
    private Float saleRate;
    private Float purchaseRate;
    private Float avgMarketSaleRate;
    private Float avgMarketPurchaseRate;
    private String date;
}
