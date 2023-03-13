package com.example.crypto_currency_watcher.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public class UserDto {
    private String userName;
    private String currencySymbol;
    private BigDecimal currencyPrice_usd;
}
