package com.example.currencyexchange.dto.external;

import lombok.Data;

@Data
public class MinfinApiExchangeRateDto {
    private String ask;
    private String bid;
    private String currency;
}
