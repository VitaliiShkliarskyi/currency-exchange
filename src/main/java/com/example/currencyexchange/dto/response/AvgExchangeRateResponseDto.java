package com.example.currencyexchange.dto.response;

import lombok.Data;

@Data
public class AvgExchangeRateResponseDto {
    private String provider;
    private String currency;
    private Float avgRate;
    private String date;
}
