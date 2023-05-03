package com.example.currencyexchange.service.api;

import java.util.List;

public interface HttpClient {
    <T> List<T> get(String url, Class<T> clazz);
}
