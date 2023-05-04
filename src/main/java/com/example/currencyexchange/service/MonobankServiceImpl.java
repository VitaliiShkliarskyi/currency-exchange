package com.example.currencyexchange.service;

import com.example.currencyexchange.dto.external.MonobankApiExchangeRateDto;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.api.HttpClient;
import com.example.currencyexchange.service.mapper.api.ProviderMapper;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonobankServiceImpl extends AbstractProviderService implements ProviderService {
    private final String providerApi;
    private final HttpClient httpClient;
    private final ProviderMapper<MonobankApiExchangeRateDto> monobankMapper;

    public MonobankServiceImpl(@Value("${monobank.provider.name}")String providerName,
                               @Value("${monobank.api.url}")String providerApi,
                               HttpClient httpClient,
                               ExchangeRateRepository exchangeRateRepository,
                               ProviderMapper<MonobankApiExchangeRateDto> monobankMapper) {
        super(exchangeRateRepository, providerName);
        this.providerApi = providerApi;
        this.httpClient = httpClient;
        this.monobankMapper = monobankMapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 30 9 * * *", zone = "GMT+3")
    @Override
    public void syncExchangeRate() {
        List<MonobankApiExchangeRateDto> mbApiExchangeRateDtoList =
                httpClient.get(providerApi, MonobankApiExchangeRateDto.class);
        mbApiExchangeRateDtoList.stream()
                .map(monobankMapper::parseApiExchangeDto)
                .forEach(super::saveUniqueExchangeRate);
    }
}
