package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findBySymbol(String symbol);
    List<Currency> findAllBySymbolIn (List<String> symbol);
}
