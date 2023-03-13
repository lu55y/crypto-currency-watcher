package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.model.Currency;

import java.util.List;

public interface CurrencyService {

    Currency getById(Long id);
    Currency getBySymbol(String name);
    void save(Currency currency);
    void delete(Long id);
    List<Currency> getAll();
}
