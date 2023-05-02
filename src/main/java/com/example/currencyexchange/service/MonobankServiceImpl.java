package com.example.currencyexchange.service;

import java.util.List;
import com.example.currencyexchange.dto.external.MonobankApiExchangeRateDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.mapper.MonobankMapper;
import org.springframework.stereotype.Service;

@Service
public class MonobankServiceImpl implements ProviderService {
    private static final String PROVIDER_API = "https://api.monobank.ua/bank/currency";
    private final HttpClient httpClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final MonobankMapper monobankMapper;

    public MonobankServiceImpl(HttpClient httpClient,
                               ExchangeRateRepository exchangeRateRepository,
                               MonobankMapper monobankMapper) {
        this.httpClient = httpClient;
        this.exchangeRateRepository = exchangeRateRepository;
        this.monobankMapper = monobankMapper;
    }

    @Override
    public void syncExchangeRate() {
        List<MonobankApiExchangeRateDto> mbApiExchangeRateDtoList =
                httpClient.get(PROVIDER_API, MonobankApiExchangeRateDto.class);
        mbApiExchangeRateDtoList.stream()
                .map(monobankMapper::parseApiExchangeDto)
                .forEach(this::saveUniqueExchangeRate);
    }

    private void saveUniqueExchangeRate(ExchangeRate rateToSave) {
        ExchangeRate existingRate = exchangeRateRepository.findByDateAndCurrencyAndProvider(
                rateToSave.getDate(), rateToSave.getCurrency(), rateToSave.getProvider());
        if (existingRate != null) {
            existingRate.setPurchaseRate(rateToSave.getPurchaseRate());
            existingRate.setSaleRate(rateToSave.getSaleRate());
            exchangeRateRepository.save(existingRate);
        } else {
            exchangeRateRepository.save(rateToSave);
        }
    }
}
