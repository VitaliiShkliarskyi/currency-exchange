package com.example.currencyexchange.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.example.currencyexchange.dto.ExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.service.ProviderService;
import com.example.currencyexchange.service.mapper.ExchangeRateMapper;
import com.example.currencyexchange.service.mapper.ResponseMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rates")
public class ExchangeRateController {
    private final List<ProviderService> providerServiceList;
    private final ResponseMapper<ExchangeRate> mapper;

    public ExchangeRateController(List<ProviderService> providerServiceList,
                                  ExchangeRateMapper mapper) {
        this.providerServiceList = providerServiceList;
        this.mapper = mapper;
    }

    /**
     * http://localhost:8080/rates
     */
    @GetMapping
    public List<ExchangeRateResponseDto> getAllWithMarketAverageRate() {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider -> allRates.addAll(provider.getAll()));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    /**
     * http://localhost:8080/rates?date=2023-05-03
     **/
    @GetMapping(params = {"date"})
    public List<ExchangeRateResponseDto> getAllByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRates.addAll(provider.getAllByDate(date)));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    /**
     * http://localhost:8080/rates?start-date=2023-05-02&end-date=2023-05-03
     **/
    @GetMapping(params = {"start-date", "end-date"})
    public List<ExchangeRateResponseDto> getAllByDateBetween(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFrom,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateTo) {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRates.addAll(provider.getAllByDateBetween(dateFrom, dateTo)));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
