package com.example.currencyexchange.controller;

import com.example.currencyexchange.service.MinfinServiceImpl;
import com.example.currencyexchange.service.MonobankServiceImpl;
import com.example.currencyexchange.service.PrivatServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class Demo {
    private final MonobankServiceImpl monobankService;
    private final MinfinServiceImpl minfinService;
    private final PrivatServiceImpl privatService;

    public Demo(MonobankServiceImpl monobankService,
                MinfinServiceImpl minfinService,
                PrivatServiceImpl privatService) {
        this.monobankService = monobankService;
        this.minfinService = minfinService;
        this.privatService = privatService;
    }

    @GetMapping("/mono")
    public String runMono() {
        monobankService.syncExchangeRate();
        return "Monobank done!";
    }

    @GetMapping("/minfin")
    public String runMinfin() {
        minfinService.syncExchangeRate();
        return "Minfin done!";
    }

    @GetMapping("/privat")
    public String runPrivat() {
        privatService.syncExchangeRate();
        return "Privat done!";
    }
}
