package com.example.currencyexchange.controller;

import com.example.currencyexchange.service.MinfinServiceImpl;
import com.example.currencyexchange.service.MonobankServiceImpl;
import com.example.currencyexchange.service.PrivatServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController  {
    private final MonobankServiceImpl monobankService;
    private final MinfinServiceImpl minfinService;
    private final PrivatServiceImpl privateService;

    public DemoController(MonobankServiceImpl monobankService,
                          MinfinServiceImpl minfinService,
                          PrivatServiceImpl privateService) {
        this.monobankService = monobankService;
        this.minfinService = minfinService;
        this.privateService = privateService;
    }

    @GetMapping("/mono")
    public String runDemoMonobank() {
        monobankService.syncExchangeRate();
        return "Monobank done!";
    }

    @GetMapping("/minfin")
    public String runDemoMinfin() {
        minfinService.syncExchangeRate();
        return "Minfin done!";
    }

    @GetMapping("/privat")
    public String runDemoPrivat() {
        privateService.syncExchangeRate();
        return "Privat done!";
    }
}
