package com.example.currencyexchange.dto.response;

import lombok.Data;

@Data
public class ExchangeRateResponseDto {
    private String provider;
    private String currency;
    private Float saleRate;
    private Float purchaseRate;
    private String date;
}
