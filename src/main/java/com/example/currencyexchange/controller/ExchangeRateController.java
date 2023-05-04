package com.example.currencyexchange.controller;

import com.example.currencyexchange.dto.response.AvgExchangeRateResponseDto;
import com.example.currencyexchange.dto.response.ExchangeRateResponseDto;
import com.example.currencyexchange.dto.response.ExchangeRateWithAvgMarketRateResponseDto;
import com.example.currencyexchange.dto.response.MarketRateResponseDto;
import com.example.currencyexchange.model.ExchangeRate;
import com.example.currencyexchange.service.ProviderService;
import com.example.currencyexchange.service.mapper.response.ResponseMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rates")
public class ExchangeRateController {
    private final List<ProviderService> providerServiceList;
    private final ResponseMapper<List<ExchangeRateResponseDto>,
            MarketRateResponseDto> marketRateMapper;
    private final ResponseMapper<ExchangeRate, ExchangeRateResponseDto> exchangeRateMapper;
    private final ResponseMapper<ExchangeRate, AvgExchangeRateResponseDto> avgExchangeRateMapper;

    public ExchangeRateController(List<ProviderService> providerServiceList,
                                  ResponseMapper<List<ExchangeRateResponseDto>,
                                          MarketRateResponseDto> marketRateMapper,
                                  ResponseMapper<ExchangeRate,
                                          ExchangeRateResponseDto> exchangeRateMapper,
                                  ResponseMapper<ExchangeRate,
                                          AvgExchangeRateResponseDto> avgExchangeRateMapper) {
        this.providerServiceList = providerServiceList;
        this.marketRateMapper = marketRateMapper;
        this.exchangeRateMapper = exchangeRateMapper;
        this.avgExchangeRateMapper = avgExchangeRateMapper;
    }

    /**
     * http://localhost:8080/rates
     */
    @GetMapping
    @ApiOperation(
            value = "Get all actual exchange rates for all sources, with average market rates")
    public ExchangeRateWithAvgMarketRateResponseDto getAllWithMarketAverageRate() {
        List<ExchangeRate> allRatesFromDb = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRatesFromDb.addAll(provider.getAllByDate(LocalDate.now())));
        List<ExchangeRateResponseDto> exchangeRateDtoList = allRatesFromDb.stream()
                .map(exchangeRateMapper::toResponseDto)
                .toList();
        Map<String, List<ExchangeRateResponseDto>> ratesByCurrency = exchangeRateDtoList.stream()
                .collect(Collectors.groupingBy(ExchangeRateResponseDto::getCurrency));
        List<MarketRateResponseDto> marketRatesDtos = ratesByCurrency.values().stream()
                .map(marketRateMapper::toResponseDto).toList();
        ExchangeRateWithAvgMarketRateResponseDto exchangeRateWithAvgMarketRateResponseDto =
                new ExchangeRateWithAvgMarketRateResponseDto();
        exchangeRateWithAvgMarketRateResponseDto.setExchangeRates(exchangeRateDtoList);
        exchangeRateWithAvgMarketRateResponseDto.setMarketRates(marketRatesDtos);
        return exchangeRateWithAvgMarketRateResponseDto;
    }

    /**
     * http://localhost:8080/rates/by-date?value=2023-05-03
     **/
    @GetMapping("/by-date")
    @ApiOperation(value = "Get all average exchange rates for all sources for a specific day")
    public List<AvgExchangeRateResponseDto> getAllByDate(
            @RequestParam("value") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(name = "value",
                    value = "The date for which to retrieve exchange rates in ISO 8601 format",
                    example = "2023-05-02")
            LocalDate date) {
        List<ExchangeRate> allRates = new ArrayList<>();
        providerServiceList.forEach(provider ->
                allRates.addAll(provider.getAllByDate(date)));
        return allRates.stream()
                .map(avgExchangeRateMapper::toResponseDto)
                .toList();
    }

    /**
     * http://localhost:8080/rates/period?start-date=2023-05-02&end-date=2023-05-03
     **/
    @GetMapping("/period")
    @ApiOperation(value = "Get all average exchange rates for all sources for a period")
    public List<AvgExchangeRateResponseDto> getAllByDateBetween(
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
                .map(avgExchangeRateMapper::toResponseDto)
                .toList();
    }
}
