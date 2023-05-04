package com.example.currencyexchange.service.mapper.response;

public interface ResponseMapper<T, V> {
    V toResponseDto(T t);
}
