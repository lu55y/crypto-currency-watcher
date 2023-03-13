package com.example.crypto_currency_watcher.dto;

import com.example.crypto_currency_watcher.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinLoreCurrencyDto {
    private Long id;
    private String symbol;
    private String name;
    private BigDecimal price_usd;

    public Currency toCurrency(){
        Currency currency = new Currency();
        currency.setId(id);
        currency.setSymbol(symbol);
        currency.setName(name);
        currency.setPriceUsd(price_usd);

        return currency;
    }
}

