package com.example.currencyexchange.service;

import java.time.LocalDate;
import java.util.List;
import com.example.currencyexchange.dto.external.MinfinApiExchangeRateDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.api.HttpClient;
import com.example.currencyexchange.service.mapper.api.MinfinMapper;
import com.example.currencyexchange.service.mapper.api.ProviderMapper;
import com.example.currencyexchange.util.MinfinUserKey;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MinfinServiceImpl implements ProviderService {
    private static final String PROVIDER_NAME = "Minfin";
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private static final String PROVIDER_API = "https://api.minfin.com.ua/mb/";
    private final HttpClient httpClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ProviderMapper<MinfinApiExchangeRateDto> minfinMapper;

    public MinfinServiceImpl(HttpClient httpClient,
                             ExchangeRateRepository exchangeRateRepository,
                             MinfinMapper minfinMapper) {
        this.httpClient = httpClient;
        this.exchangeRateRepository = exchangeRateRepository;
        this.minfinMapper = minfinMapper;
    }

    //@PostConstruct
    @Scheduled(cron = "0 30 9 * * *", zone = "GMT+3")
    @Override
    public void syncExchangeRate() {
        List<MinfinApiExchangeRateDto> minfinApiExchangeRateDtoList =
                httpClient.get(PROVIDER_API + MinfinUserKey.USER_KEY,
                        MinfinApiExchangeRateDto.class);
        minfinApiExchangeRateDtoList.stream()
                .map(minfinMapper::parseApiExchangeDto)
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
