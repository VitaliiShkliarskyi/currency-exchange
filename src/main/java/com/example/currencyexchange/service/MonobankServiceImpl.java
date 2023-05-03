package com.example.currencyexchange.service;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import com.example.currencyexchange.dto.external.MonobankApiExchangeRateDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.api.HttpClient;
import com.example.currencyexchange.service.mapper.api.MonobankMapper;
import com.example.currencyexchange.service.mapper.api.ProviderMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonobankServiceImpl implements ProviderService {
    private static final String PROVIDER_NAME = "Monobank";
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private static final String PROVIDER_API = "https://api.monobank.ua/bank/currency";
    private final HttpClient httpClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ProviderMapper<MonobankApiExchangeRateDto> monobankMapper;

    public MonobankServiceImpl(HttpClient httpClient,
                               ExchangeRateRepository exchangeRateRepository,
                               MonobankMapper monobankMapper) {
        this.httpClient = httpClient;
        this.exchangeRateRepository = exchangeRateRepository;
        this.monobankMapper = monobankMapper;
    }


    @PostConstruct
    @Scheduled(cron = "0 30 9 * * *", zone = "GMT+3")
    @Override
    public void syncExchangeRate() {
        List<MonobankApiExchangeRateDto> mbApiExchangeRateDtoList =
                httpClient.get(PROVIDER_API, MonobankApiExchangeRateDto.class);
        mbApiExchangeRateDtoList.stream()
                .map(monobankMapper::parseApiExchangeDto)
                .forEach(this::saveUniqueExchangeRate);
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateRepository.findAllByProviderAndDate(PROVIDER_NAME, CURRENT_DATE);
    }

    @Override
    public List<ExchangeRate> getAllByDate(LocalDate date) {
        return exchangeRateRepository.findAllByProviderAndDate(PROVIDER_NAME, date);
    }

    @Override
    public List<ExchangeRate> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return exchangeRateRepository
                .findAllByProviderAndDateBetween(PROVIDER_NAME, dateFrom, dateTo);
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
