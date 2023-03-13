package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    Currency findBySymbol(String name);
}
