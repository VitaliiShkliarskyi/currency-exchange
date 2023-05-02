package com.example.currencyexchange.service;

import java.util.List;
import com.example.currencyexchange.dto.external.MinfinApiExchangeRateDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.mapper.MinfinMapper;
import org.springframework.stereotype.Service;


@Service
public class MinfinServiceImpl implements ProviderService {
    private static final String PROVIDER_API = "https://api.minfin.com.ua/mb/";
    private static final String MINFIN_USER_KEY = "7ce543a7cd455b5af6e3c8df2dde86ebe636832a/";
    private final HttpClient httpClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final MinfinMapper minfinMapper;

    public MinfinServiceImpl(HttpClient httpClient,
                             ExchangeRateRepository exchangeRateRepository,
                             MinfinMapper minfinMapper) {
        this.httpClient = httpClient;
        this.exchangeRateRepository = exchangeRateRepository;
        this.minfinMapper = minfinMapper;
    }

    @Override
    public void syncExchangeRate() {
        List<MinfinApiExchangeRateDto> minfinApiExchangeRateDtoList =
                httpClient.get(PROVIDER_API + MINFIN_USER_KEY, MinfinApiExchangeRateDto.class);
        minfinApiExchangeRateDtoList.stream()
                .map(minfinMapper::parseApiExchangeDto)
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
