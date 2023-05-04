package com.example.currencyexchange.service;

import com.example.currencyexchange.dto.external.MinfinApiExchangeRateDto;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.api.HttpClient;
import com.example.currencyexchange.service.mapper.api.ProviderMapper;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MinfinServiceImpl extends AbstractProviderService implements ProviderService {

    private final String providerApi;
    private final String userKey;
    private final HttpClient httpClient;
    private final ProviderMapper<MinfinApiExchangeRateDto> minfinMapper;

    public MinfinServiceImpl(@Value("${minfin.provider.name}")String providerName,
                             @Value("${minfin.api.url}")String providerApi,
                             @Value("${minfin.user.key}")String userKey,
                             HttpClient httpClient,
                             ExchangeRateRepository exchangeRateRepository,
                             ProviderMapper<MinfinApiExchangeRateDto> minfinMapper) {
        super(exchangeRateRepository, providerName);
        this.providerApi = providerApi;
        this.userKey = userKey;
        this.httpClient = httpClient;
        this.minfinMapper = minfinMapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 30 9 * * *", zone = "GMT+3")
    @Override
    public void syncExchangeRate() {
        List<MinfinApiExchangeRateDto> minfinApiExchangeRateDtoList =
                httpClient.get(providerApi + userKey,
                        MinfinApiExchangeRateDto.class);
        minfinApiExchangeRateDtoList.stream()
                .map(minfinMapper::parseApiExchangeDto)
                .forEach(super::saveUniqueExchangeRate);
    }
}
