package com.example.currencyexchange.service.mapper;

import com.example.currencyexchange.dto.ExchangeRateResponseDto;

public interface ResponseMapper<T> {
    ExchangeRateResponseDto toResponseDto(T model);
}
