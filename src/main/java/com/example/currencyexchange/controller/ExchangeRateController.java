package com.example.currencyexchange.controller;

import com.example.currencyexchange.dto.ExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.service.MinfinServiceImpl;
import com.example.currencyexchange.service.MonobankServiceImpl;
import com.example.currencyexchange.service.PrivatServiceImpl;
import com.example.currencyexchange.service.ProviderService;
import com.example.currencyexchange.service.mapper.ExchangeRateMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rates")
public class ExchangeRateController {
    private final List<ProviderService> providerServiceList;
    private final ExchangeRateMapper mapper;

    public ExchangeRateController(List<ProviderService> providerServiceList,
                                  ExchangeRateMapper mapper) {
        this.providerServiceList = providerServiceList;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ExchangeRateResponseDto> getAllWithMarketAverageRate() {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider -> allRates.addAll(provider.getAll()));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @GetMapping(params = {"start-date", "end-date"})
    public void getAllByDateBetween(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFrom,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateTo) {
        System.out.println("OK");

    }

    @GetMapping(params = {"date"})
    public void getAllByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFrom) {
        System.out.println("OKOK");

    }

}
