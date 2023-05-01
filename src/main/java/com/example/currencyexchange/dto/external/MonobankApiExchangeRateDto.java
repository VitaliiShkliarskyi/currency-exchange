package com.example.currencyexchange.dto.external;

import lombok.Data;

@Data
public class MonobankApiExchangeRateDto {
    private float rateSell;
    private float rateBuy;
    private int currencyCodeA;
}
