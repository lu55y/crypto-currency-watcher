package com.example.crypto_currency_watcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class CurrencyResponse {
    private Long id;
    private String symbol;
    private String name;
    private BigDecimal price_usd;
}
