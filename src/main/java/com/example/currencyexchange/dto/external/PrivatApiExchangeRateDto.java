package com.example.currencyexchange.dto.external;

import lombok.Data;

@Data
public class PrivatApiExchangeRateDto {
    private String sale;
    private String buy;
    private String ccy;
}
