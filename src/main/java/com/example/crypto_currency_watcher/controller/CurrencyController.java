package com.example.crypto_currency_watcher.controller;

import com.example.crypto_currency_watcher.dto.CurrencyResponse;
import com.example.crypto_currency_watcher.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies/")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyResponse> findBySymbol(@RequestParam List<String> symbol) {
        return currencyService.findCurrencyBySymbol(symbol);
    }

/*    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getCurrencies();
    }*/
}
