package com.example.currencyexchange.dto.external;

import lombok.Data;

@Data
public class PrivatApiExchangeRate {
    private String sale;
    private String buy;
    private String ccy;
}
