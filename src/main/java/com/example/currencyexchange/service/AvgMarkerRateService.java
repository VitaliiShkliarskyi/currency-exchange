package com.example.currencyexchange.service;

import com.example.currencyexchange.model.Currency;
import java.time.LocalDate;

public interface AvgMarkerRateService {
    Float getAverageMarketSaleRate(Currency currency, LocalDate date);

    Float getAverageMarketPurchaseRate(Currency currency, LocalDate date);
}
