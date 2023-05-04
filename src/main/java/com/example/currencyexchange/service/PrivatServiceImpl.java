package com.example.currencyexchange.service;

import com.example.currencyexchange.dto.external.PrivatApiExchangeRateDto;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.api.HttpClient;
import com.example.currencyexchange.service.mapper.api.ProviderMapper;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PrivatServiceImpl extends AbstractProviderService implements ProviderService {
    private final String providerApi;
    private final HttpClient httpClient;
    private final ProviderMapper<PrivatApiExchangeRateDto> privatMapper;

    public PrivatServiceImpl(@Value("${privatbank.provider.name}")String providerName,
                             @Value("${privatbank.api.url}")String providerApi,
                             HttpClient httpClient,
                             ExchangeRateRepository exchangeRateRepository,
                             ProviderMapper<PrivatApiExchangeRateDto> privatMapper) {
        super(exchangeRateRepository, providerName);
        this.providerApi = providerApi;
        this.httpClient = httpClient;
        this.privatMapper = privatMapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 30 9 * * *", zone = "GMT+3")
    @Override
    public void syncExchangeRate() {
        List<PrivatApiExchangeRateDto> pbApiExchangeRateDtoList =
                httpClient.get(providerApi, PrivatApiExchangeRateDto.class);
        pbApiExchangeRateDtoList.stream()
                .map(privatMapper::parseApiExchangeDto)
                .forEach(super::saveUniqueExchangeRate);
    }
}
