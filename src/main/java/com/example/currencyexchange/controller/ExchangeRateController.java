package com.example.currencyexchange.controller;

import com.example.currencyexchange.dto.ExchangeRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.service.ProviderService;
import com.example.currencyexchange.service.mapper.ExchangeRateMapper;
import com.example.currencyexchange.service.mapper.ResponseMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @ApiOperation(
            value = "Get all actual exchange rates for all sources, with average market rates")
    public List<ExchangeRateResponseDto> getAllWithMarketAverageRate() {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider -> allRates.addAll(provider.getAll()));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    /**
     * http://localhost:8080/rates/by-date?value=2023-05-03
     **/
    @GetMapping("/by-date")
    @ApiOperation(value = "Get all average exchange rates for all sources for for a specific day")
    public List<ExchangeRateResponseDto> getAllByDate(
            @RequestParam("value") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(name = "value",
                    value = "The date for which to retrieve exchange rates in ISO 8601 format",
                    example = "2023-05-02")
            LocalDate date) {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRates.addAll(provider.getAllByDate(date)));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    /**
     * http://localhost:8080/rates/period?start-date=2023-05-02&end-date=2023-05-03
     **/
    @GetMapping("/period")
    @ApiOperation(value = "Get all average exchange rates for all sources for a period")
    public List<ExchangeRateResponseDto> getAllByDateBetween(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(name = "start-date",
                    value = "The start date of the period to retrieve exchange rates in ISO 8601",
                    example = "2023-11-26")
            LocalDate dateFrom,
            @RequestParam(name = "end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(name = "end-date",
                    value = "The end date of the period to retrieve exchange rates in ISO 8601",
                    example = "2023-12-15")
            LocalDate dateTo) {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRates.addAll(provider.getAllByDateBetween(dateFrom, dateTo)));
        return allRates.stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
