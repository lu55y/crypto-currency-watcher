package com.example.crypto_currency_watcher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String currencySymbol;
    private BigDecimal currencyPrice_usd;
}
