package com.example.currencyexchange.controller;

import com.example.currencyexchange.dto.external.MinfinApiExchangeRateDto;
import com.example.currencyexchange.dto.external.MonobankApiExchangeRateDto;
import com.example.currencyexchange.dto.external.PrivatApiExchangeRate;
import com.example.currencyexchange.service.HttpClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController  {
    private final HttpClient httpClient;

    public DemoController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping("/mono")
    public String runDemoMonobank() {
        List<MonobankApiExchangeRateDto> monobankApiExchangeRateDtoList =
                httpClient.get("https://api.monobank.ua/bank/currency",
                        MonobankApiExchangeRateDto.class);
        System.out.println(monobankApiExchangeRateDtoList);
        return "Monobank done!";
    }

    @GetMapping("/minfin")
    public String runDemoMinfin() {
        List<MinfinApiExchangeRateDto> minfinApiExchangeRateDtoList =
                httpClient.get("https://api.minfin.com.ua/mb/7ce543a7cd455b5af6e3c8df2dde86ebe636832a/",
                        MinfinApiExchangeRateDto.class);
        System.out.println(minfinApiExchangeRateDtoList);
        return "Minfin done!";
    }

    @GetMapping("/privat")
    public String runDemoPrivat() {
        List<PrivatApiExchangeRate> privatApiExchangeRateList =
                httpClient.get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5",
                        PrivatApiExchangeRate.class);
        System.out.println(privatApiExchangeRateList);
        return "Privat done!";
    }

}
