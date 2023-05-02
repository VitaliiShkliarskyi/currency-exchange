package com.example.currencyexchange.service;

import java.time.LocalDate;
import java.util.List;
import com.example.currencyexchange.dto.external.PrivatApiExchangeRateDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.mapper.api.PrivatMapper;
import org.springframework.stereotype.Service;

@Service
public class PrivatServiceImpl implements ProviderService {
    private static final String PROVIDER_NAME = "PrivatBank";
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private static final String PROVIDER_API = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private final HttpClient httpClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final PrivatMapper privatMapper;

    public PrivatServiceImpl(HttpClient httpClient, ExchangeRateRepository exchangeRateRepository, PrivatMapper privatMapper) {
        this.httpClient = httpClient;
        this.exchangeRateRepository = exchangeRateRepository;
        this.privatMapper = privatMapper;
    }

    @Override
    public void syncExchangeRate() {
        List<PrivatApiExchangeRateDto> pbApiExchangeRateDtoList =
                httpClient.get(PROVIDER_API, PrivatApiExchangeRateDto.class);
        pbApiExchangeRateDtoList.stream()
                .map(privatMapper::parseApiExchangeDto)
                .forEach(this::saveUniqueExchangeRate);
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateRepository.findAllByProviderAndDate(PROVIDER_NAME, CURRENT_DATE);
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
